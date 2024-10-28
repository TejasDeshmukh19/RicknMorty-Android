package com.ricknmorty.characters.data.usecases_impl

import com.helpers.network.Loading
import com.ricknmorty.data.domain.repos.ICharactersRepository
import com.ricknmorty.data.domain.usecases.characters.IGetAllCharactersUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

class GetAllCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: ICharactersRepository
) : IGetAllCharactersUseCase {

    override suspend fun invoke(page: Int) = flow {
        emit(Loading(isLoading = true))
        emit(charactersRepository.getCharacters(page))
    }
}