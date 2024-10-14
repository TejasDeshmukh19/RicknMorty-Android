package com.ricknmorty.ui_resources.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Tejas Deshmukh on 04/10/24.
 */

@Composable
fun NoInternetLayout(
    modifier: Modifier,
    isNoNetworkAvailable: Boolean,
    action: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    if (isNoNetworkAvailable) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outlineVariant),
                shape = RoundedCornerShape(50)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(72.dp),
                        imageVector = Icons.Filled.SignalWifiConnectedNoInternet4,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )

                    FilledIconButton(
                        modifier = Modifier.size(72.dp),
                        onClick = { action?.invoke() },
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            modifier = Modifier.size(36.dp),
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    } else {
        content()
    }
}