package com.example.popcorn.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popcorn.data.local.db.TVShowsDatabase
import com.example.popcorn.data.local.entity.TVShow
import io.reactivex.Completable
import io.reactivex.Flowable

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val tvShowsDatabase: TVShowsDatabase = TVShowsDatabase.getTvShowDatabase(application)

    fun loadFavoritesList(): Flowable<List<TVShow>>{
        return tvShowsDatabase.tvShowDao().getWatchlist()
    }

    fun removeTVShowFromFavorites(tvShow: TVShow): Completable{
        return tvShowsDatabase.tvShowDao().removeFromWatchlist(tvShow)
    }



}