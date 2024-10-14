package com.ricknmorty.characters.presentation.characters_listing.viewmodel

import androidx.lifecycle.viewModelScope
import com.helpers.base.qualifiers.IoDispatcher
import com.helpers.base.qualifiers.MainDispatcher
import com.helpers.base.viewmodel.BaseViewModel
import com.helpers.network.ApiError
import com.helpers.network.ApiException
import com.helpers.network.Success
import com.helpers.network.interceptors.NetworkInterceptor
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
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getAllCharactersUseCase: IGetAllCharactersUseCase,
    private val characterToCharacterUIModel: CharacterToCharacterUIModel
) : BaseViewModel<CharactersListState, CharactersListEvents, Nothing>() {

    override val initialState: CharactersListState get() = CharactersListState()

    private var isLoadingNewPage: Boolean = false
    override fun onEvent(event: CharactersListEvents) {
        when (event) {
            is OnLoadNextPage -> {
                if (isLoadingNewPage) {
                    return
                }

                isLoadingNewPage = true
                render(state.value.copy(isLoading = true, isNoInternetConnectivity = false))

                viewModelScope.launch(ioDispatcher) {
                    when (val response =
                        getAllCharactersUseCase.invoke(state.value.nextPageIndex)) {
                        is Success -> {
                            withContext(mainDispatcher) {
                                render(
                                    state.value.copy(
                                        characters = state.value.characters + ((response.data.results
                                            ?: emptyList()).mapNotNull {
                                            characterToCharacterUIModel.map(it)
                                        }),
                                        isLoading = false,
                                        nextPageIndex = state.value.nextPageIndex + 1,
                                        allPagesLoaded = response.data.results.isNullOrEmpty()
                                    )
                                )
                            }
                            isLoadingNewPage = false
                        }

                        is ApiError -> {
                            isLoadingNewPage = false
                            withContext(mainDispatcher) {
                                render(
                                    state.value.copy(
                                        isLoading = false, allPagesLoaded = true
                                    )
                                )
                            }
                        }

                        is ApiException -> {
                            isLoadingNewPage = false
                            if (response.exception is NetworkInterceptor.NoNetworkException ||
                                response.exception is UnknownHostException
                            ) {
                                render(
                                    state.value.copy(
                                        isLoading = false, isNoInternetConnectivity = true
                                    )
                                )
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