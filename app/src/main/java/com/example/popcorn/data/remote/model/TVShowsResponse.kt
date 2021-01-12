package com.example.popcorn.data.remote.model
import com.example.popcorn.data.local.entity.TVShow
import com.google.gson.annotations.SerializedName

data class TVShowsResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("tv_shows")
    val TVShows: List<TVShow>
)