package com.example.popcorn.data.local.entity

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episode")
    val episode: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("season")
    val season: String
)