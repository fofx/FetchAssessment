package com.fofxlabs.fetchassessment.ui.screens

import androidx.lifecycle.viewModelScope
import com.fofxlabs.fetchassessment.R
import com.fofxlabs.fetchassessment.dagger.DefaultDispatcher
import com.fofxlabs.fetchassessment.dagger.IoDispatcher
import com.fofxlabs.fetchassessment.data.DataRepository
import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.data.externalModel.sortByListIdAndId
import com.fofxlabs.fetchassessment.ui.BaseViewModel
import com.fofxlabs.fetchassessment.utils.UniqueWrapper
import com.fofxlabs.fetchassessment.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

data class ListUiState(
    val isLoading: Boolean = false,
    val snackbarMessage: UniqueWrapper<Int>? = null,
    val items: List<Item> = emptyList(),
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): BaseViewModel() {
    val items: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())

    val uiState: StateFlow<ListUiState> = combine(
        isLoading,
        snackbarMessage,
        items
    ) { isLoading, snackbarMessage, items ->
        ListUiState(
            isLoading = isLoading,
            snackbarMessage = snackbarMessage,
            items = items
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = ListUiState()
    )

    companion object {
        const val GET_ITEM_STREAM = 0
        const val REFRESH_ITEMS = 1
    }

    init {
        getItemsStream()

        // Fetch from online if not already cached in DB
        // TODO: Depending on requirements, add a cache timeout to refresh data automatically
        viewModelScope.launch {
            val isAlreadyCached = dataRepository.areItemsAlreadyCached()
            if (!isAlreadyCached) {
                refreshListItems()
            }
        }
    }

    // Fetch list of items from local db and sort
    private fun getItemsStream() {
        viewModelScope.launch {
            setIsLoading(GET_ITEM_STREAM, true)

            dataRepository.getItemsStream()
                .catch {
                    setIsLoading(GET_ITEM_STREAM, false)

                    Timber.e(it)
                    // TODO: Can be more descriptive with error messages based on exception type
                    showSnackbarMessage(R.string.something_went_wrong)
                }
                .collect { items ->
                    withContext(defaultDispatcher) {
                        // Sort by listId and and then by id
                        this@ListViewModel.items.value = items.sortByListIdAndId()
                        setIsLoading(GET_ITEM_STREAM, false)
                    }
                }
        }
    }

    // Refresh list of items from online
    fun refreshListItems() {
        viewModelScope.launch {
            setIsLoading(REFRESH_ITEMS, true)

            try {
                dataRepository.refreshItems()
                showSnackbarMessage(R.string.refreshed_successfully)
            }
            catch (exception: Exception) {
                Timber.e(exception)
                showSnackbarMessage(R.string.something_went_wrong)
            }
            finally {
                setIsLoading(REFRESH_ITEMS, false)
            }
        }
    }
}