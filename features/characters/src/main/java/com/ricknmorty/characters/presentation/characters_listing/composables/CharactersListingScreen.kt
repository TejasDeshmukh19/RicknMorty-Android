package com.ricknmorty.characters.presentation.characters_listing.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.CharactersListViewModel
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListEvents
import com.ricknmorty.ui_resources.composables.NoInternetLayout

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharactersListingScreen(
    viewModel: CharactersListViewModel = hiltViewModel(),
    goToCharacterDetails: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        NoInternetLayout(
            modifier = Modifier.fillMaxSize(),
            isNoNetworkAvailable = state.value.isNoInternetConnectivity && state.value.characters.isEmpty(),
            action = { viewModel.onEvent(CharactersListEvents.OnLoadNextPage) }
        ) {
            CharacterListingContent(
                state = state.value,
                onLoadNextPage = {
                    viewModel.onEvent(CharactersListEvents.OnLoadNextPage)
                },
                onToggleViewType = {
                    viewModel.onEvent(CharactersListEvents.OnToggleViewType)
                },
                goToCharacterDetails = goToCharacterDetails,
                sharedTransitionScope,
                animatedContentScope
            )
        }
    }
}


