package com.ricknmorty.characters.presentation.character_details.viewmodel.models

import com.helpers.base.viewmodel.IViewModel
import com.ricknmorty.data.ui_models.CharacterDetailsUIModel
import com.ricknmorty.data.ui_models.EpisodeUIModel

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val characterDetails: CharacterDetailsUIModel? = null,
    val episodes: List<EpisodeUIModel> = emptyList(),
    val isPersonalInfoExpanded: Boolean = true,
    val isEpisodesExpanded: Boolean = false,
    val isNoInternetConnectivity: Boolean = false
) : IViewModel.State
