package com.ricknmorty.router.routes

import kotlinx.serialization.Serializable

/**
 * Created by Tejas Deshmukh on 26/09/24.
 */

@Serializable
data object CharactersListingScreenRoute

@Serializable
data class CharacterDetailsScreenRoute(val characterId: Int)