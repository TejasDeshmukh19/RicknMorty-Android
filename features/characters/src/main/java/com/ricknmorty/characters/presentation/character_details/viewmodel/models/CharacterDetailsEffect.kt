package com.ricknmorty.characters.presentation.character_details.viewmodel.models

import com.helpers.base.viewmodel.IViewModel
import com.ricknmorty.data.ui_models.EpisodeUIModel

/**
 * Created by Tejas Deshmukh on 02/10/24.
 */

sealed interface CharacterDetailsEffect : IViewModel.Effect {
    data class ShowEpisodeDetails(val episode: EpisodeUIModel) : CharacterDetailsEffect
    data class ShowMessage(val message: String) : CharacterDetailsEffect
}