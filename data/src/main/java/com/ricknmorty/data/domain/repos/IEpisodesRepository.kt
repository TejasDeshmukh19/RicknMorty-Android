package com.ricknmorty.data.domain.repos

import com.helpers.network.ApiResponse
import com.ricknmorty.data.responses.EpisodeDto

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */

interface IEpisodesRepository {
    suspend fun getSelectedEpisodes(episodes: String): ApiResponse<List<EpisodeDto>>
}