package com.ricknmorty.ui_resources.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.helpers.base.viewmodel.IViewModel
import com.ricknmorty.ui_resources.R

/**
 * Created by Tejas Deshmukh on 03/10/24.
 */

@Composable
fun NoInternetScreen(
    modifier: Modifier,
    event: IViewModel.Event,
    performEventAgain: (IViewModel.Event) -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(20.dp)
            .clickable { performEventAgain(event) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(R.drawable.ic_no_internet), contentDescription = null)
        Spacer(modifier = Modifier.height(12.dp))
        Text("No Internet Connectivity")
    }
}