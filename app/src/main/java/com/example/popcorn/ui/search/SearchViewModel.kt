package com.example.popcorn.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popcorn.data.remote.model.TVShowsResponse
import com.example.popcorn.repositories.SearchTVShowRepository

class SearchViewModel : ViewModel() {

    private val searchTVShowRepository: SearchTVShowRepository = SearchTVShowRepository()

    fun searchTVShow(query: String, page: Int): LiveData<TVShowsResponse>{
        return searchTVShowRepository.searchTVShow(query, page)
    }
}