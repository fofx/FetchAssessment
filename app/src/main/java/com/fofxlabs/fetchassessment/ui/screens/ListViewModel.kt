package com.fofxlabs.fetchassessment.ui.screens

import androidx.lifecycle.viewModelScope
import com.fofxlabs.fetchassessment.R
import com.fofxlabs.fetchassessment.data.DataRepository
import com.fofxlabs.fetchassessment.data.network.model.NetworkItem
import com.fofxlabs.fetchassessment.ui.BaseViewModel
import com.fofxlabs.fetchassessment.utils.UniqueWrapper
import com.fofxlabs.fetchassessment.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ListUiState(
    val isLoading: Boolean = false,
    val snackbarMessage: UniqueWrapper<Int>? = null,
    val items: List<NetworkItem> = emptyList(),
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dataRepository: DataRepository
): BaseViewModel() {
    val networkItems: MutableStateFlow<List<NetworkItem>> = MutableStateFlow(emptyList())

    val uiState: StateFlow<ListUiState> = combine(
        isLoading,
        snackbarMessage,
        networkItems
    ) { isLoading, snackbarMessage, networkItems ->
        ListUiState(
            isLoading = isLoading,
            snackbarMessage = snackbarMessage,
            items = networkItems
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = ListUiState()
    )

    companion object {
        const val GET_LIST_ITEMS = 0
    }

    init {
        getListItems()
    }

    fun getListItems() {
        viewModelScope.launch {
            setIsLoading(GET_LIST_ITEMS, true)

            try {
                // Fetch list of items and sort by listId and and then by id
                networkItems.value = dataRepository.getItems().body()
                    ?.filter { it.name != null && it.name.isNotBlank() }
                    ?.sortedWith(compareBy<NetworkItem> { it.listId }.thenBy { it.id })
                    ?: emptyList()
            }
            catch (exception: Exception) {
                Timber.e(exception)
                showSnackbarMessage(R.string.something_went_wrong)
            }
            finally {
                setIsLoading(GET_LIST_ITEMS, false)
            }
        }
    }
}