package com.ricknmorty.characters.presentation.character_details.viewmodel

import androidx.lifecycle.viewModelScope
import com.helpers.base.qualifiers.IoDispatcher
import com.helpers.base.viewmodel.BaseViewModel
import com.helpers.network.NetworkError
import com.helpers.network.ServerError
import com.helpers.network.onFailure
import com.helpers.network.onSuccess
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsEffect
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsEvents
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsState
import com.ricknmorty.data.domain.usecases.characters.IGetCharacterDetailsUseCase
import com.ricknmorty.data.domain.usecases.episodes.IGetSelectedEpisodesUseCase
import com.ricknmorty.data.mappers.CharacterToCharacterDetailsUIModel
import com.ricknmorty.data.mappers.EpisodeDtoEpisodeUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getCharacterDetailsUseCase: IGetCharacterDetailsUseCase,
    private val getSelectedEpisodesUseCase: IGetSelectedEpisodesUseCase,
    private val characterToCharacterDetailsUIModel: CharacterToCharacterDetailsUIModel,
    private val episodeDtoEpisodeUIModel: EpisodeDtoEpisodeUIModel
) : BaseViewModel<CharacterDetailsState, CharacterDetailsEvents, CharacterDetailsEffect>() {

    override val initialState: CharacterDetailsState get() = CharacterDetailsState(isLoading = true)

    override fun onEvent(event: CharacterDetailsEvents) {
        when (event) {
            is CharacterDetailsEvents.FetchCharacterDetails -> {
                fetchCharacterDetails(characterId = event.characterId)
            }

            is CharacterDetailsEvents.OnTogglePersonalInfo -> {
                render(state.value.copy(isPersonalInfoExpanded = state.value.isPersonalInfoExpanded.not()))
            }

            is CharacterDetailsEvents.OnToggleEpisodesInfo -> {
                val isExpanded = state.value.isEpisodesExpanded
                val onlyRefresh = event.onlyRefreshRequired

                if (!isExpanded || onlyRefresh)
                    render(
                        state.value.copy(
                            isEpisodesExpanded = true,
                            isPersonalInfoExpanded = false,
                            isNoInternetConnectivity = false
                        )
                    )
                else
                    render(
                        state.value.copy(
                            isEpisodesExpanded = false,
                            isNoInternetConnectivity = false
                        )
                    )
                if (state.value.isEpisodesExpanded && state.value.episodes.isEmpty()) {
                    fetchEpisodeDetails()
                }
            }

            is CharacterDetailsEvents.OnShowEpisodeDetails -> {
                state.value.episodes.firstOrNull { it.id == event.episodeId }?.let {
                    sendEffect(CharacterDetailsEffect.ShowEpisodeDetails(it))
                }
            }
        }
    }


    private fun fetchCharacterDetails(characterId: Int) {
        render(state.value.copy(isLoading = true, isNoInternetConnectivity = false))
        viewModelScope.launch(ioDispatcher) {

            val response = getCharacterDetailsUseCase.invoke(characterId)

            response.onSuccess { responseData ->
                render(
                    CharacterDetailsState(
                        isLoading = false,
                        characterDetails = characterToCharacterDetailsUIModel.map(
                            responseData
                        )
                    )
                )
            }.onFailure { error ->
                when (error) {
                    is NetworkError -> {
                        render(state.value.copy(isLoading = false, isNoInternetConnectivity = true))
                    }

                    is ServerError -> {
                        render(newState = state.value.copy(isLoading = false))
                        sendEffect(CharacterDetailsEffect.ShowMessage(error.message))
                    }

                }
            }
        }
    }


    private fun fetchEpisodeDetails() {
        viewModelScope.launch(ioDispatcher) {
            state.value.characterDetails?.episodeIds?.let {

                val response = getSelectedEpisodesUseCase.getSelectedEpisodes(it)

                response.onSuccess { responseData ->
                    render(
                        state.value.copy(
                            episodes = responseData.map { episodeDto ->
                                episodeDtoEpisodeUIModel.map(
                                    episodeDto
                                )
                            },
                            isEpisodesExpanded = true,
                            isNoInternetConnectivity = false
                        )
                    )
                }.onFailure { error ->
                    when (error) {
                        is NetworkError -> {
                            render(
                                state.value.copy(
                                    isLoading = false, isNoInternetConnectivity = true
                                )
                            )
                        }

                        is ServerError -> {
                            render(newState = state.value.copy(isLoading = false))
                            sendEffect(CharacterDetailsEffect.ShowMessage(error.message))
                        }
                    }
                }
            }
        }
    }
}