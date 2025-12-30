package com.example.project_uas.Data.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SloganApiClient {

    private const val BASE_URL = "https://cvkhkojpseezixecsbul.supabase.co/rest/v1/"

    val api: SloganApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SloganApiService::class.java)
    }
}
