package com.ricknmorty.characters.data.repos_impl

import com.helpers.network.ApiResponse
import com.ricknmorty.data.domain.apis.ICharactersApi
import com.ricknmorty.data.domain.repos.ICharactersRepository
import com.ricknmorty.data.responses.Character
import com.ricknmorty.data.responses.CharactersPageDto
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

class CharactersRepository @Inject constructor(private val charactersApi: ICharactersApi) :
    ICharactersRepository {

    override suspend fun getCharacters(page: Int): ApiResponse<CharactersPageDto> {
        return charactersApi.getCharactersFor(page = page)
    }

    override suspend fun getCharacterDetails(characterId: Int): ApiResponse<Character> {
        return charactersApi.getCharacterDetails(characterId = characterId)
    }
}