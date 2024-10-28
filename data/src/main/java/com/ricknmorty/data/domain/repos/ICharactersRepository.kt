package com.ricknmorty.data.domain.repos

import com.helpers.network.ApiResponse
import com.ricknmorty.data.responses.Character
import com.ricknmorty.data.responses.CharactersPageDto

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

interface ICharactersRepository {
    suspend fun getCharacters(page: Int): ApiResponse<CharactersPageDto>
    suspend fun getCharacterDetails(characterId: Int): ApiResponse<Character>
}