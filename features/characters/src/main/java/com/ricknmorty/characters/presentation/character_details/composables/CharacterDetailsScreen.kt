package com.ricknmorty.characters.presentation.character_details.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ricknmorty.characters.presentation.character_details.viewmodel.CharacterDetailsViewModel
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsEffect
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsEffect.ShowEpisodeDetails
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsEvents
import com.ricknmorty.data.ui_models.EpisodeUIModel
import com.ricknmorty.ui_resources.composables.MessageDialog
import com.ricknmorty.ui_resources.composables.NoInternetLayout
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Tejas Deshmukh on 28/09/24.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    var showMessage by remember { mutableStateOf<String?>(null) }
    val lifeCycleOwner = LocalLifecycleOwner.current
    var showEpisodeDetails by remember {
        mutableStateOf<EpisodeUIModel?>(null)
    }

    LaunchedEffect(key1 = lifeCycleOwner) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collectLatest {
                when (it) {
                    is ShowEpisodeDetails -> {
                        showEpisodeDetails = it.episode
                    }

                    is CharacterDetailsEffect.ShowMessage -> {
                        showMessage = it.message
                    }
                }
            }
        }
    }

    if (showMessage != null) {
        MessageDialog(showMessage!!) {
            showMessage = null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NoInternetLayout(
            modifier = Modifier.fillMaxSize(),
            action = {
                viewModel.onEvent(CharacterDetailsEvents.FetchCharacterDetails(characterId = characterId))
            },
            isNoNetworkAvailable = state.value.isNoInternetConnectivity && state.value.characterDetails == null
        ) {
            CharacterDetailsScreenContent(
                onFetchCharacterDetails = {
                    viewModel.onEvent(CharacterDetailsEvents.FetchCharacterDetails(characterId = characterId))
                },
                onTogglePersonalInfo = {
                    viewModel.onEvent(CharacterDetailsEvents.OnTogglePersonalInfo)
                },
                onToggleEpisodesInfo = {
                    //Here we actually toggle Episodes list.
                    viewModel.onEvent(
                        CharacterDetailsEvents.OnToggleEpisodesInfo(
                            onlyRefreshRequired = false
                        )
                    )
                },
                onRefreshEpisodes = {
                    //Here we just refresh already opened Episodes list.
                    viewModel.onEvent(
                        CharacterDetailsEvents.OnToggleEpisodesInfo(
                            onlyRefreshRequired = true
                        )
                    )
                },
                onShowEpisodeDetails = { episodeId ->
                    viewModel.onEvent(CharacterDetailsEvents.OnShowEpisodeDetails(episodeId = episodeId))
                }, onNavigateBack,
                state = state.value,
                sharedTransitionScope,
                animatedContentScope
            )

            showEpisodeDetails?.let {
                SelectedEpisodeSheet(episode = it) {
                    showEpisodeDetails = null
                }
            }
        }
    }
}