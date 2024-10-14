package com.ricknmorty.episodes.data.apis_impl

import com.helpers.network.ApiResponse
import com.helpers.network.apiCall
import com.ricknmorty.data.domain.apis.IEpisodesApi
import com.ricknmorty.data.responses.EpisodeDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */

class EpisodesApi @Inject constructor(private val retrofit: Retrofit) : IEpisodesApi {

    private val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }

    override suspend fun getSelectedEpisodes(episodes: String): ApiResponse<List<EpisodeDto>> {
        return apiCall { apiService.getSelectedEpisodes(episodes = episodes) }
    }

    interface ApiService {

        @GET("episode/{episodes}")
        suspend fun getSelectedEpisodes(@Path("episodes") episodes: String): Response<List<EpisodeDto>>
    }
}