package com.fofxlabs.fetchassessment.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fofxlabs.fetchassessment.R
import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.ui.components.MainTopAppBar
import com.fofxlabs.fetchassessment.ui.components.Snackbar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ListViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopAppBar(title = stringResource(R.string.app_name))
        },
        scaffoldState = scaffoldState
    ) { padding ->
        ListScreenContent(
            viewModel = viewModel,
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding))

        Snackbar(uiState.snackbarMessage, scaffoldState, viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreenContent(
    viewModel: ListViewModel,
    uiState: ListUiState,
    modifier: Modifier = Modifier.fillMaxSize()) {
    val pullRefreshState = rememberPullRefreshState(uiState.isLoading, { viewModel.refreshListItems() })

    Box(modifier = modifier
        .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            items(uiState.items, key = { item -> item.id }) { item ->
                Item(item)
            }
        }

        PullRefreshIndicator(
            refreshing = uiState.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun Item(
    item: Item,
    modifier: Modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
) {
    Column(modifier = modifier) {
        Text(
            text = "listId: ${item.listId}",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "id: ${item.id}",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "name: ${item.name}",
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()

    }
}