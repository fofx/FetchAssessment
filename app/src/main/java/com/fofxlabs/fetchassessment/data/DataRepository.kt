package com.fofxlabs.fetchassessment.data

import com.fofxlabs.fetchassessment.data.network.model.NetworkItem
import retrofit2.Response

interface DataRepository {
    suspend fun getItems(): Response<List<NetworkItem>>
}