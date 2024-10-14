package com.ricknmorty.data.mappers

import com.ricknmorty.data.responses.Character
import com.ricknmorty.data.ui_models.CharacterUIModel
import javax.inject.Inject

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */
class CharacterToCharacterUIModel @Inject constructor() : Mapper<Character, CharacterUIModel?> {
    override fun map(from: Character): CharacterUIModel? {
        return if (from.id == null) {
            null
        } else {
            CharacterUIModel(
                id = from.id,
                name = from.name,
                image = from.image
            )
        }
    }
}