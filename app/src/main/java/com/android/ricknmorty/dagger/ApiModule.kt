package com.android.ricknmorty.dagger

import com.ricknmorty.characters.data.apis_impl.CharactersApi
import com.ricknmorty.data.domain.apis.ICharactersApi
import com.ricknmorty.data.domain.apis.IEpisodesApi
import com.ricknmorty.episodes.data.apis_impl.EpisodesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideCharactersApi(api: CharactersApi): ICharactersApi = api

    @Provides
    fun provideEpisodesApi(api: EpisodesApi): IEpisodesApi = api
}