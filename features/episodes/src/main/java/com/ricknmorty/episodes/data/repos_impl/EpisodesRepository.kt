package com.ricknmorty.episodes.data.repos_impl

import com.helpers.network.ApiResponse
import com.ricknmorty.data.domain.apis.IEpisodesApi
import com.ricknmorty.data.domain.repos.IEpisodesRepository
import com.ricknmorty.data.responses.EpisodeDto
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */

class EpisodesRepository @Inject constructor(private val episodesApi: IEpisodesApi) :
    IEpisodesRepository {

    override suspend fun getSelectedEpisodes(episodes: String): ApiResponse<List<EpisodeDto>> {
        return episodesApi.getSelectedEpisodes(episodes = episodes)
    }
}