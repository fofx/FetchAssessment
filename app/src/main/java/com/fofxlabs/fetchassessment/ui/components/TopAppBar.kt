package com.fofxlabs.fetchassessment.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MainTopAppBar(title: String) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.fillMaxWidth()
    )
}