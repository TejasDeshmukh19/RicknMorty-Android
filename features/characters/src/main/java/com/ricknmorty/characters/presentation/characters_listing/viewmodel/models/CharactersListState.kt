package com.ricknmorty.characters.presentation.characters_listing.viewmodel.models

import com.helpers.base.viewmodel.IViewModel
import com.ricknmorty.data.ui_models.CharacterUIModel

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

data class CharactersListState(
    var characters: List<CharacterUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val selectedViewType: ViewType = ViewType.GRID,
    val isNoInternetConnectivity: Boolean = false
) : IViewModel.State

enum class ViewType {
    LIST,
    GRID
}
