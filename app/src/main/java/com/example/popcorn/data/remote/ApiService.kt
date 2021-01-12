package com.example.popcorn.data.remote
import com.example.popcorn.data.remote.model.TVShowDetailsResponse
import com.example.popcorn.data.remote.model.TVShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("most-popular")
    fun getMostPopularTVShows(@Query("page") page: Int): Call<TVShowsResponse>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId: String): Call <TVShowDetailsResponse>

    @GET("search")
    fun searchTVShow(@Query("q") query: String, @Query("page") page: Int): Call<TVShowsResponse>
}