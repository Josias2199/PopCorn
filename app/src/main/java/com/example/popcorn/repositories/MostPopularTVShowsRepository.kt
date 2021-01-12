package com.example.popcorn.repositories

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popcorn.common.MyApp
import com.example.popcorn.data.remote.ApiClient
import com.example.popcorn.data.remote.ApiService
import com.example.popcorn.data.remote.model.TVShowsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularTVShowsRepository {


    private var apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    fun getMostPopularTVShows(page: Int): LiveData<TVShowsResponse>{
        val data: MutableLiveData<TVShowsResponse> = MutableLiveData<TVShowsResponse>()
        val call: Call<TVShowsResponse> = apiService.getMostPopularTVShows(page)
        call.enqueue(object : Callback<TVShowsResponse>{
            override fun onResponse(
                @NonNull call: Call<TVShowsResponse>,
                @NonNull response: Response<TVShowsResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(
                @NonNull call: Call<TVShowsResponse>,
                @NonNull t: Throwable) {
                data.value = null
                Toast.makeText(MyApp.instance,"Error de red", Toast.LENGTH_SHORT).show()
            }

        })

        return data
    }

}

