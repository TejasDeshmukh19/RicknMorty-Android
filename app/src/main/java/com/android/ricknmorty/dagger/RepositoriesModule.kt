package com.android.ricknmorty.dagger

import com.ricknmorty.characters.data.repos_impl.CharactersRepository
import com.ricknmorty.data.domain.repos.ICharactersRepository
import com.ricknmorty.data.domain.repos.IEpisodesRepository
import com.ricknmorty.episodes.data.repos_impl.EpisodesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideCharactersRepository(repository: CharactersRepository): ICharactersRepository =
        repository

    @Provides
    fun provideEpisodesRepository(repository: EpisodesRepository): IEpisodesRepository =
        repository
}