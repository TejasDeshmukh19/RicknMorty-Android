package com.ricknmorty.episodes.data.usecases_impl

import com.helpers.network.ApiResponse
import com.ricknmorty.data.domain.repos.IEpisodesRepository
import com.ricknmorty.data.domain.usecases.episodes.IGetSelectedEpisodesUseCase
import com.ricknmorty.data.responses.EpisodeDto
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */

class GetSelectedEpisodesUseCase @Inject constructor(private val episodesRepository: IEpisodesRepository) :
    IGetSelectedEpisodesUseCase {
    override suspend fun getSelectedEpisodes(episodes: String): ApiResponse<List<EpisodeDto>> {
        return episodesRepository.getSelectedEpisodes(episodes = episodes)
    }
}