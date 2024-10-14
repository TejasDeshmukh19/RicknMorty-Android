package com.ricknmorty.characters.presentation.characters_listing.composables

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListState
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.ViewType
import com.ricknmorty.ui_resources.R
import com.ricknmorty.ui_resources.composables.NoInternetLayout
import com.ricknmorty.ui_resources.composables.ProgressIndicator
import kotlinx.coroutines.launch

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListingContent(
    state: CharactersListState,
    onLoadNextPage: () -> Unit,
    onToggleViewType: () -> Unit,
    goToCharacterDetails: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val scrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    val isReachedToBottom by remember {
        derivedStateOf {
            !scrollState.canScrollForward || scrollState.layoutInfo.visibleItemsInfo.isEmpty()
        }
    }

    LaunchedEffect(isReachedToBottom) {
        if (isReachedToBottom) {
            onLoadNextPage()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                selectedViewType = state.selectedViewType,
                onToggleViewType = onToggleViewType
            )
        },
    ) { padding ->
        if (state.isLoading && state.characters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                ProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            return@Scaffold
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(12.dp),
            state = scrollState,
            columns = GridCells.Fixed(if (state.selectedViewType == ViewType.LIST) 1 else 2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(state.characters) { index, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    with(sharedTransitionScope) {
                        CharacterListItem(Modifier.sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = item.image!!),
                            animatedVisibilityScope = animatedContentScope
                        ),
                            viewType = state.selectedViewType,
                            character = item,
                            onClick = { characterId ->
                                goToCharacterDetails(characterId)
                            })
                    }

                    if (state.isLoading && index == state.characters.size - 1) {
                        ProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        scope.launch {
                            scrollState.scrollToItem(scrollState.layoutInfo.totalItemsCount)
                        }
                    }

                    if (state.isNoInternetConnectivity && index == state.characters.size - 1) {
                        NoInternetLayout(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            action = onLoadNextPage,
                            isNoNetworkAvailable = true
                        ) { }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(selectedViewType: ViewType, onToggleViewType: () -> Unit) {
    CenterAlignedTopAppBar(modifier = Modifier
        .shadow(
            elevation = 6.dp
        ),
        title = {
            Text(stringResource(id = R.string.characters))
        },
        actions = {
            IconButton(onClick = onToggleViewType) {
                Icon(
                    imageVector = if (selectedViewType == ViewType.LIST) Icons.Filled.GridView else Icons.AutoMirrored.Filled.ViewList,
                    contentDescription = null
                )
            }
        }
    )
}