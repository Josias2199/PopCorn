package com.example.popcorn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.popcorn.data.local.entity.TVShow
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tvshows")
    fun getWatchlist(): Flowable<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToWatchlist(tvShow: TVShow): Completable

    @Delete
    fun removeFromWatchlist(tvShow: TVShow): Completable

    @Query("SELECT * FROM tvshows WHERE id = :id")
    fun getTVShowFromFavoritesList(id: String): Flowable<TVShow>
}