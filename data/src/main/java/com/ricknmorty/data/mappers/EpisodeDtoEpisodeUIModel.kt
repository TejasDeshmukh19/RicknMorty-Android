package com.ricknmorty.data.mappers

import com.ricknmorty.data.responses.EpisodeDto
import com.ricknmorty.data.ui_models.EpisodeUIModel
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */

class EpisodeDtoEpisodeUIModel @Inject constructor() : Mapper<EpisodeDto, EpisodeUIModel> {
    override fun map(from: EpisodeDto): EpisodeUIModel {
        return EpisodeUIModel(
            id = from.id,
            name = from.name,
            releasedOn = from.airDate,
            code = from.episode,
            characterIds = from.characters?.map { it.substringAfterLast("/") }
        )
    }
}