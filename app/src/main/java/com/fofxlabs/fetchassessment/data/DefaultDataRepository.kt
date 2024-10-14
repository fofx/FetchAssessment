package com.fofxlabs.fetchassessment.data

import com.fofxlabs.fetchassessment.dagger.IoDispatcher
import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.data.local.ItemDao
import com.fofxlabs.fetchassessment.data.network.FetchAssessmentApi
import com.fofxlabs.fetchassessment.data.network.model.NetworkItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDataRepository @Inject constructor(
    private val fetchAssessmentApi: FetchAssessmentApi,
    private val itemDao: ItemDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher): DataRepository {

    override suspend fun getItems(): Response<List<NetworkItem>>{
        return withContext(ioDispatcher) {
            fetchAssessmentApi.getItems()
        }
    }

    override fun getItemsStream(): Flow<List<Item>> {
        return itemDao.observeAllItems().map { localItems ->
            withContext(ioDispatcher) {
                localItems.toExternal()
            }
        }
    }

    override suspend fun refreshItems() {
        withContext(ioDispatcher) {
            try {
                val networkItems = fetchAssessmentApi.getItems().body()
                networkItems?.let { networkItems ->
                    itemDao.deleteAll()
                    itemDao.upsertAll(networkItems.toLocal())
                }
            } catch (exception: Exception) {
                Timber.e(exception)
                throw exception
            }
        }
    }

    override suspend fun areItemsAlreadyCached(): Boolean {
        return withContext(ioDispatcher) {
            itemDao.getAll().isNotEmpty()
        }
    }
}