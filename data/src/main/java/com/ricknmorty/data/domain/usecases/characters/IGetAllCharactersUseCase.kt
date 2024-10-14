package com.ricknmorty.data.domain.usecases.characters

import com.helpers.network.ApiResponse
import com.ricknmorty.data.responses.CharacterDto

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

interface IGetAllCharactersUseCase {
    suspend fun invoke(page: Int): ApiResponse<CharacterDto>
}