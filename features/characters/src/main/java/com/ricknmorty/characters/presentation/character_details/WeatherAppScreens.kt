package com.ricknmorty.characters.presentation.character_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun StartComposable() {
    val scope = rememberCoroutineScope()

    var myData by remember { mutableStateOf(ComposableData("ABC", 123)) }

    Column {
        val context = LocalContext.current
        CompositionLocalProvider(LocalComposableDemo provides myData) {
            ComposableDemo1()
            ComposableDemo2()
            CompositionLocalProvider(LocalComposableDemo provides ComposableData("PQR", 456)) {
                ComposableDemo3()
            }
        }
        ComposableDemo4 {
            scope.launch {
                myData =  ComposableData("XYZ", 999)
            }
        }
    }
}



data class ComposableData(val someKey1: String, val someKey2: Int)

val LocalComposableDemo = compositionLocalOf<ComposableData> { error("No value provided") }


@Composable
fun ComposableDemo1() {
    println("ComposableDemo1")
    val composableData = LocalComposableDemo.current
    Text(text = "Key1: ${composableData.someKey1}, Key2: ${composableData.someKey2}")
}

@Composable
fun ComposableDemo2() {
    println("ComposableDemo2")
    val composableData = LocalComposableDemo.current
    Column {
        Text(text = "Key1: ${composableData.someKey1}, Key2: ${composableData.someKey2}")
        ComposableDemo2_1()
    }
}

@Composable
fun ComposableDemo2_1() {
    println("ComposableDemo2_1")
    Text(text = "Not using CompositionLocal")
}

@Composable
fun ComposableDemo3() {
    println("ComposableDemo3")
    val composableData = LocalComposableDemo.current
    Text(text = "Key1: ${composableData.someKey1}, Key2: ${composableData.someKey2}")
}

@Composable
fun ComposableDemo4(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Update value")
    }
}