package com.example.popcorn.data.remote.model

import com.example.popcorn.data.local.entity.TvShowDetails
import com.google.gson.annotations.SerializedName

data class TVShowDetailsResponse(
    @SerializedName("tvShow")
    val tvShow: TvShowDetails
)