package com.android.ricknmorty.app_navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ricknmorty.characters.presentation.character_details.composables.CharacterDetailsScreen
import com.ricknmorty.characters.presentation.characters_listing.composables.CharactersListingScreen
import com.ricknmorty.router.routes.CharacterDetailsScreenRoute
import com.ricknmorty.router.routes.CharactersListingScreenRoute

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(navController: NavHostController) {
    SharedTransitionLayout(
        modifier = Modifier,
    ) {
        NavHost(
            navController = navController,
            startDestination = CharactersListingScreenRoute,
        ) {
            composable<CharactersListingScreenRoute> {
                CharactersListingScreen(sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable, goToCharacterDetails = {
                        navController.navigate(route = CharacterDetailsScreenRoute(it))
                    })
            }

            composable<CharacterDetailsScreenRoute> {
                CharacterDetailsScreen(
                    (it.toRoute() as CharacterDetailsScreenRoute).characterId,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}