package com.example.project_uas.Data.Api

import com.example.project_uas.Data.Model.SloganModel
import retrofit2.http.GET
import retrofit2.http.Header

interface SloganApiService {
    @GET("slogan_hewan?select=*")
    suspend fun getSlogans(
        @Header("apikey") apiKey: String,
        @Header("Authorization") token: String
    ): List<SloganModel>
}
