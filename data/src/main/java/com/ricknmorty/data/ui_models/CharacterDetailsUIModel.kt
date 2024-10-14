package com.ricknmorty.data.ui_models

import androidx.annotation.StringRes

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */
data class CharacterDetailsUIModel(
    val id: Int?,
    val name: String?,
    val image: String?,
    val personalInfo: List<PersonalInfo> = emptyList(),
    val episodeIds: String?
)

data class PersonalInfo(
    @StringRes val label: Int,
    val data: String?
)
