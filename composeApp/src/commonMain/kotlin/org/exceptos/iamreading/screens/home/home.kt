package org.exceptos.iamreading.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun HomeScreen() {

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Testing writing compose faster and smoother")
        Text("Testing writing compose faster and smoother")
        Text("Testing writing compose faster and smoother")

    }

}