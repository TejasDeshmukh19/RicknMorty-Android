package com.ricknmorty.data.responses

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

data class CharactersPageDto(
    val info: PaginationInfo?,
    val results: List<Character>?
)

data class PaginationInfo(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
)

data class Character(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Location?,
    val image: String?,
    val episode: List<String>?,
    val url: String?,
    val created: String?
)

data class Origin(
    val name: String?,
    val url: String?,
)

data class Location(
    val name: String?,
    val url: String?,
)