package com.ricknmorty.characters.data.apis_impl

import com.helpers.network.ApiResponse
import com.helpers.network.apiCall
import com.ricknmorty.data.domain.apis.ICharactersApi
import com.ricknmorty.data.responses.Character
import com.ricknmorty.data.responses.CharactersPageDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

class CharactersApi @Inject constructor(retrofit: Retrofit) : ICharactersApi {

    private val apiService = retrofit.create(ApiService::class.java)

    override suspend fun getCharactersFor(page: Int): ApiResponse<CharactersPageDto> {
        return apiCall { apiService.getCharacters(page = page) }
    }

    override suspend fun getCharacterDetails(characterId: Int): ApiResponse<Character> {
        return apiCall { apiService.getCharacterDetails(characterId = characterId) }
    }

    interface ApiService {

        @GET("character")
        suspend fun getCharacters(@Query("page") page: Int): Response<CharactersPageDto>

        @GET("character/{characterId}")
        suspend fun getCharacterDetails(@Path("characterId") characterId: Int): Response<Character>
    }
}