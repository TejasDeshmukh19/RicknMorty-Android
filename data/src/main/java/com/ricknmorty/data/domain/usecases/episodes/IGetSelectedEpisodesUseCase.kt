package com.ricknmorty.data.domain.usecases.episodes

import com.helpers.network.ApiResponse
import com.ricknmorty.data.responses.EpisodeDto

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */


interface IGetSelectedEpisodesUseCase {
    suspend fun getSelectedEpisodes(episodes: String): ApiResponse<List<EpisodeDto>>
}