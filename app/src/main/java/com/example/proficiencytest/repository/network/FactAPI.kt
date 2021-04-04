package com.example.proficiencytest.repository.network

import com.example.proficiencytest.model.response.Fact
import retrofit2.Response
import retrofit2.http.GET

// All Network requests
interface FactAPI {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    suspend fun getFacts(): Response<Fact>
}