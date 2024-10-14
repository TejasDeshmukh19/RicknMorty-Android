package com.ricknmorty.ui_resources.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(50.dp)
            .padding(12.dp)
    )
}