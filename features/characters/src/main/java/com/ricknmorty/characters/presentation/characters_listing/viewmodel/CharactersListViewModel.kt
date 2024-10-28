package com.ricknmorty.characters.presentation.characters_listing.viewmodel

import androidx.lifecycle.viewModelScope
import com.helpers.base.qualifiers.IoDispatcher
import com.helpers.base.viewmodel.BaseViewModel
import com.helpers.network.NetworkError
import com.helpers.network.ServerError
import com.helpers.network.onFailure
import com.helpers.network.onLoading
import com.helpers.network.onSuccess
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListEvents
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListEvents.OnLoadNextPage
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListEvents.OnToggleViewType
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListState
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.ViewType
import com.ricknmorty.data.domain.usecases.characters.IGetAllCharactersUseCase
import com.ricknmorty.data.mappers.CharacterToCharacterUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getAllCharactersUseCase: IGetAllCharactersUseCase,
    private val characterToCharacterUIModel: CharacterToCharacterUIModel
) : BaseViewModel<CharactersListState, CharactersListEvents, Nothing>() {

    override val initialState: CharactersListState get() = CharactersListState()

    private var isLoadingNewPage: Boolean = false
    private var pageIndex = 1
    private var isAllPagesLoaded: Boolean = false

    override fun onEvent(event: CharactersListEvents) {
        when (event) {
            is OnLoadNextPage -> {
                if (isLoadingNewPage || isAllPagesLoaded) {
                    return
                }

                viewModelScope.launch(ioDispatcher) {
                    getAllCharactersUseCase.invoke(pageIndex).collect { response ->
                        response.onLoading {
                            isLoadingNewPage = true
                            render(
                                state.value.copy(
                                    isLoading = it,
                                    isNoInternetConnectivity = false
                                )
                            )
                        }.onSuccess {
                            if (pageIndex < (it.info?.count ?: 0)) {
                                pageIndex++
                            } else {
                                isAllPagesLoaded = true
                            }

                            render(
                                state.value.copy(
                                    characters = state.value.characters + ((it.results
                                        ?: emptyList()).mapNotNull {
                                        characterToCharacterUIModel.map(it)
                                    }),
                                    isLoading = false
                                )
                            )
                            isLoadingNewPage = false
                        }.onFailure {
                            isLoadingNewPage = false
                            when (it) {
                                is NetworkError -> {
                                    render(
                                        state.value.copy(
                                            isLoading = false,
                                            isNoInternetConnectivity = true
                                        )
                                    )
                                }

                                is ServerError -> {
                                    isAllPagesLoaded = true
                                    render(
                                        state.value.copy(isLoading = false)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            OnToggleViewType -> {
                val currentViewType = state.value.selectedViewType
                render(
                    state.value.copy(
                        selectedViewType = if (currentViewType == ViewType.LIST) ViewType.GRID else ViewType.LIST
                    )
                )
            }
        }
    }
}