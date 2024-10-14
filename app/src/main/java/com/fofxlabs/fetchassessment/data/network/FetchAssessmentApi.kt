package com.fofxlabs.fetchassessment.data.network

import com.fofxlabs.fetchassessment.data.network.model.NetworkItem
import retrofit2.Response
import retrofit2.http.GET

interface FetchAssessmentApi {
    @GET("hiring.json")
    suspend fun getItems(): Response<List<NetworkItem>>
}