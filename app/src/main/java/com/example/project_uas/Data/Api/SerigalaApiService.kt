package com.example.project_uas.Data.Api

import com.example.project_uas.Data.Model.SerigalaModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SerigalaApiService {
    @GET("animals")
    suspend fun getAnimalsByName(
        @Query("name") name: String,
        @Header("X-Api-Key") apiKey: String
    ): List<SerigalaModel>
}
