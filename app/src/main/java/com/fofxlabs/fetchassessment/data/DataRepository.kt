package com.fofxlabs.fetchassessment.data

import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.data.network.model.NetworkItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DataRepository {
    suspend fun getItems(): Response<List<NetworkItem>>

    fun getItemsStream(): Flow<List<Item>>

    suspend fun refreshItems()

    suspend fun areItemsAlreadyCached(): Boolean

}