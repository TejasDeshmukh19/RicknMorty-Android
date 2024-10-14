package com.ricknmorty.data.mappers

import com.ricknmorty.data.responses.Character
import com.ricknmorty.data.ui_models.CharacterDetailsUIModel
import com.ricknmorty.data.ui_models.PersonalInfo
import com.ricknmorty.ui_resources.R
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

class CharacterToCharacterDetailsUIModel @Inject constructor() :
    Mapper<Character, CharacterDetailsUIModel> {
    override fun map(from: Character): CharacterDetailsUIModel {
        return CharacterDetailsUIModel(
            id = from.id,
            name = from.name,
            image = from.image,
            personalInfo = listOf(
                PersonalInfo(label = R.string.gender, data = from.gender),
                PersonalInfo(label = R.string.status, data = from.status),
                PersonalInfo(label = R.string.species, data = from.species),
                PersonalInfo(label = R.string.type, data = from.type?.ifEmpty { "UNKNOWN" }),
                PersonalInfo(label = R.string.origin_name, data = from.origin?.name),
                PersonalInfo(label = R.string.location, data = from.location?.name)
            ),
            episodeIds = from.episode?.joinToString(postfix = ",") {
                it.substringAfterLast("/")
            }?.replace(" ", "")
        )
    }
}