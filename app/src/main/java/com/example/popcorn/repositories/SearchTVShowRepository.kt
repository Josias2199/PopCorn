package com.example.popcorn.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popcorn.data.remote.ApiClient
import com.example.popcorn.data.remote.ApiService
import com.example.popcorn.data.remote.model.TVShowsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class SearchTVShowRepository {
    private val apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    fun searchTVShow(query: String, page: Int): LiveData<TVShowsResponse>{
        val data = MutableLiveData<TVShowsResponse>()
        apiService.searchTVShow(query, page).enqueue(object : Callback<TVShowsResponse>{
            override fun onResponse(
                @NonNull call: Call<TVShowsResponse>,
                @NonNull response: Response<TVShowsResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(@NonNull call: Call<TVShowsResponse>, @NonNull t: Throwable) {
                data.value = null
            }

        })
        return data
    }
}