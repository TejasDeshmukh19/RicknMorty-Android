package com.ricknmorty.characters.data.usecases_impl

import com.helpers.network.ApiResponse
import com.ricknmorty.data.domain.repos.ICharactersRepository
import com.ricknmorty.data.domain.usecases.characters.IGetCharacterDetailsUseCase
import com.ricknmorty.data.responses.Character
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

class GetCharacterDetailsUseCase @Inject constructor(
    private val charactersRepository: ICharactersRepository
) : IGetCharacterDetailsUseCase {

    override suspend fun invoke(characterId: Int): ApiResponse<Character> {
        return charactersRepository.getCharacterDetails(characterId)
    }
}