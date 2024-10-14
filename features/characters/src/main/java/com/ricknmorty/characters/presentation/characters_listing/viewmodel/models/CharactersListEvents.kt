package com.ricknmorty.characters.presentation.characters_listing.viewmodel.models

import com.helpers.base.viewmodel.IViewModel

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

sealed interface CharactersListEvents : IViewModel.Event {
    data object OnLoadNextPage : CharactersListEvents
    data object OnToggleViewType : CharactersListEvents
}
