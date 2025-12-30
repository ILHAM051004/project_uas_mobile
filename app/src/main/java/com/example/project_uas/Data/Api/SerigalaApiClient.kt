package com.example.project_uas.Data.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SerigalaApiClient {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/"

    val apiService: HarimauApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HarimauApiService::class.java)
    }
}