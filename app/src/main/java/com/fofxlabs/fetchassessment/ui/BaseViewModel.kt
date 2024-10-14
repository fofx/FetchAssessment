package com.fofxlabs.fetchassessment.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.fofxlabs.fetchassessment.utils.UniqueWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


/**
 * BaseViewModel that contains common features for screens
 */
open class BaseViewModel() : ViewModel() {
    protected val snackbarMessage: MutableStateFlow<UniqueWrapper<Int>?> = MutableStateFlow(null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // list of ids of loading tasks
    protected val isLoadingList = mutableListOf<Int>()

    private val mutex = Mutex()

    fun snackbarMessageShown() {
        snackbarMessage.value = null
    }

    // Snackbar messages wrapped in UniqueWrapper
    // in order to display same message multiple times if needed
    protected fun showSnackbarMessage(@StringRes message: Int) {
        snackbarMessage.value = UniqueWrapper(message)
    }

    // When there are multiple network calls,
    // this function will ensure that the loading state is only turned off
    // once all network calls are finished.
    // Use unique ids for each network call or function that requires setting the loading state.
    protected suspend fun setIsLoading(id: Int, isLoading: Boolean) {
        mutex.withLock {
            if (isLoading) {
                isLoadingList.add(id)
                _isLoading.value = true
            } else {
                isLoadingList.remove(id)
                if (isLoadingList.isEmpty()) {
                    _isLoading.value = false
                }
            }
        }
    }
}