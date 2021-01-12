package com.example.popcorn.data.local.entity

import com.google.gson.annotations.SerializedName

data class TvShowDetails(
    @SerializedName("description")
    val description: String,

    @SerializedName("episodes")
    val episodes: List<Episode>,

    @SerializedName("genres")
    val genres: List<String>,

    @SerializedName("image_path")
    val image_path: String,

    @SerializedName("pictures")
    val pictures: List<String>,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("runtime")
    val runtime: String,

    @SerializedName("url")
    val url: String,

)