package com.fofxlabs.fetchassessment.ui.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.fofxlabs.fetchassessment.ui.BaseViewModel
import com.fofxlabs.fetchassessment.utils.UniqueWrapper

@Composable
fun Snackbar(uniqueWrapper: UniqueWrapper?, scaffoldState: ScaffoldState, viewModel: BaseViewModel) {
    if (uniqueWrapper != null) {
        val snackbarText = when (uniqueWrapper) {
            is UniqueWrapper.IntWrapper -> stringResource(uniqueWrapper.value)
            is UniqueWrapper.StringWrapper -> uniqueWrapper.value
        }

        LaunchedEffect(scaffoldState, viewModel, uniqueWrapper) {
            scaffoldState.snackbarHostState.showSnackbar(snackbarText)
            viewModel.snackbarMessageShown()
        }
    }
}