package com.example.popcorn.ui.home.principal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.popcorn.repositories.MostPopularTVShowsRepository
import com.example.popcorn.data.remote.model.TVShowsResponse

class PrincipalViewModel : ViewModel() {

    private var mostPopularTVShowsRepository: MostPopularTVShowsRepository = MostPopularTVShowsRepository()

    fun getMostPopularTVShows(page: Int): LiveData<TVShowsResponse>{
        return mostPopularTVShowsRepository.getMostPopularTVShows(page)
    }
}