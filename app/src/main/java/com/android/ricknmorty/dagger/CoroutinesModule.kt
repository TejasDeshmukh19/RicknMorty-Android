package com.android.ricknmorty.dagger

import com.helpers.base.qualifiers.IoDispatcher
import com.helpers.base.qualifiers.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Tejas Deshmukh on 03/10/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @IoDispatcher
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun mainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}