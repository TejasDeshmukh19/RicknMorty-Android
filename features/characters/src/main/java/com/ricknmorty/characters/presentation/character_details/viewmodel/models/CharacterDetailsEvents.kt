package com.ricknmorty.characters.presentation.character_details.viewmodel.models

import com.helpers.base.viewmodel.IViewModel

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

sealed interface CharacterDetailsEvents : IViewModel.Event {
    data class FetchCharacterDetails(val characterId: Int) : CharacterDetailsEvents
    data object OnTogglePersonalInfo : CharacterDetailsEvents
    data class OnToggleEpisodesInfo(val onlyRefreshRequired: Boolean) : CharacterDetailsEvents
    data class OnShowEpisodeDetails(val episodeId: Int) : CharacterDetailsEvents
}