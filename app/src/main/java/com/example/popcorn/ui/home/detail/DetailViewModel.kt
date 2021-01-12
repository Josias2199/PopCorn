package com.example.popcorn.ui.home.detail

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.popcorn.data.local.db.TVShowsDatabase
import com.example.popcorn.data.local.entity.TVShow
import com.example.popcorn.repositories.TVShowDetailsRepository
import com.example.popcorn.data.remote.model.TVShowDetailsResponse
import io.reactivex.Completable
import io.reactivex.Flowable

class DetailViewModel(@NonNull application: Application) : AndroidViewModel(application) {

    private var tvShowDetailsRepository: TVShowDetailsRepository = TVShowDetailsRepository()
    private var tvShowsDatabase: TVShowsDatabase = TVShowsDatabase.getTvShowDatabase(application)

    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse>{
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }

    fun addToWatchlist(tvShow: TVShow): Completable{
        return tvShowsDatabase.tvShowDao().addToWatchlist(tvShow)
    }

    fun getTVShowFromFavoritesList(id: String): Flowable<TVShow> {
        return tvShowsDatabase.tvShowDao().getTVShowFromFavoritesList(id)
    }

    fun removeTVShowFromFavoritesList(tvShow: TVShow): Completable{
        return tvShowsDatabase.tvShowDao().removeFromWatchlist(tvShow)
    }
}