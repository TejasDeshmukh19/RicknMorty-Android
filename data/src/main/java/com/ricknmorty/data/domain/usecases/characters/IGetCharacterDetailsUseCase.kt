package com.ricknmorty.data.domain.usecases.characters

import com.helpers.network.ApiResponse
import com.ricknmorty.data.responses.Character

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */
interface IGetCharacterDetailsUseCase {
    suspend fun invoke(characterId: Int): ApiResponse<Character>
}