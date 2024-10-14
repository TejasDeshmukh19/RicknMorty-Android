package com.ricknmorty.characters.presentation.character_details.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsState
import com.ricknmorty.ui_resources.composables.ProgressIndicator

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterDetailsScreenContent(
    onFetchCharacterDetails: () -> Unit,
    onTogglePersonalInfo: () -> Unit,
    onToggleEpisodesInfo: () -> Unit,
    onRefreshEpisodes: () -> Unit,
    onShowEpisodeDetails: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    state: CharacterDetailsState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        onFetchCharacterDetails()
    }

    with(sharedTransitionScope) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(onNavigateBack = onNavigateBack)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(state = scrollState)
            ) {
                AnimatedVisibility(state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        ProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                state.characterDetails?.apply {
                    CharacterImage(
                        Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(
                                    key = image ?: ""
                                ),
                                animatedVisibilityScope = animatedContentScope
                            ), image = image
                    )

                    CharacterName(name = name)

                    InformationContainer(
                        isExpanded = state.isPersonalInfoExpanded,
                        characterDetails = this,
                        onToggle = onTogglePersonalInfo
                    )

                    EpisodesContainer(
                        isExpanded = state.isEpisodesExpanded,
                        isNetworkAvailable = state.isNoInternetConnectivity,
                        episodes = state.episodes,
                        onToggle = onToggleEpisodesInfo,
                        onRefreshEpisodes = onRefreshEpisodes,
                        onEpisodeClicked = onShowEpisodeDetails
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onNavigateBack: () -> Unit) {
    CenterAlignedTopAppBar(modifier = Modifier
        .shadow(
            elevation = 6.dp
        ),
        title = {

        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}


@Composable
fun CharacterImage(modifier: Modifier, image: String?) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4F),
            model = image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun CharacterName(name: String?) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp), text = name ?: "",
        style = MaterialTheme.typography.titleLarge,
        maxLines = 2
    )
}