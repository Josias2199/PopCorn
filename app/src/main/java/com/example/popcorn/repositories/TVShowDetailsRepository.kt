package com.example.popcorn.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popcorn.data.remote.ApiClient
import com.example.popcorn.data.remote.ApiService
import com.example.popcorn.data.remote.model.TVShowDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {
    private var apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse>{
        val data: MutableLiveData<TVShowDetailsResponse> = MutableLiveData<TVShowDetailsResponse>()
        val call: Call<TVShowDetailsResponse> = apiService.getTVShowDetails(tvShowId)
        call.enqueue(object : Callback<TVShowDetailsResponse>{
            override fun onResponse(
                @NonNull call: Call<TVShowDetailsResponse>,
                @NonNull response: Response<TVShowDetailsResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(@NonNull call: Call<TVShowDetailsResponse>, @NonNull t: Throwable) {
                data.value = null
            }

        })
        return data
    }
}