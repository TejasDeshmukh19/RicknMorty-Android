package com.ricknmorty.data.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */
data class EpisodeDto(
    val id: Int?,
    val name: String?,
    @SerializedName("air_date")
    val airDate: String?,
    val episode: String?,
    val characters: List<String>?,
    val url: String?,
    val created: String?
)
