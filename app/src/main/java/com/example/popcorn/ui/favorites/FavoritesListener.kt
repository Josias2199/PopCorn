package com.example.popcorn.ui.favorites

import com.example.popcorn.data.local.entity.TVShow

interface FavoritesListener {

    fun removeTVShowFromFavoritesList(tvShow: TVShow, position: Int)
}