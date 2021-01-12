package com.example.popcorn.data.remote

import com.example.popcorn.common.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    /*private lateinit var retrofit: Retrofit

    private fun getRetrofit(): Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }*/

    companion object     {
        fun getRetrofit(): Retrofit{
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            //val apiService: ApiService = retrofit.create(ApiService::class.java)
            return retrofit
        }
    }

}