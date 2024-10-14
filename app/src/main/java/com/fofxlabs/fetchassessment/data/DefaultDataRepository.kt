package com.fofxlabs.fetchassessment.data

import com.fofxlabs.fetchassessment.dagger.IoDispatcher
import com.fofxlabs.fetchassessment.data.network.FetchAssessmentApi
import com.fofxlabs.fetchassessment.data.network.model.NetworkItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDataRepository @Inject constructor(
    private val fetchAssessmentApi: FetchAssessmentApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher): DataRepository {

    override suspend fun getItems(): Response<List<NetworkItem>>{
        return withContext(ioDispatcher) {
            fetchAssessmentApi.getItems()
        }
    }
}