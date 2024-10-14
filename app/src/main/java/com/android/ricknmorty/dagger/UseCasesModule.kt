package com.android.ricknmorty.dagger

import com.ricknmorty.characters.data.usecases_impl.GetAllCharactersUseCaseImpl
import com.ricknmorty.characters.data.usecases_impl.GetCharacterDetailsUseCase
import com.ricknmorty.data.domain.usecases.characters.IGetAllCharactersUseCase
import com.ricknmorty.data.domain.usecases.characters.IGetCharacterDetailsUseCase
import com.ricknmorty.data.domain.usecases.episodes.IGetSelectedEpisodesUseCase
import com.ricknmorty.episodes.data.usecases_impl.GetSelectedEpisodesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetAllCharactersUseCase(useCase: GetAllCharactersUseCaseImpl): IGetAllCharactersUseCase =
        useCase

    @Provides
    fun provideGetCharacterDetailsUseCase(useCase: GetCharacterDetailsUseCase): IGetCharacterDetailsUseCase =
        useCase

    @Provides
    fun provideGetSelectedEpisodesUseCase(useCase: GetSelectedEpisodesUseCase): IGetSelectedEpisodesUseCase =
        useCase
}