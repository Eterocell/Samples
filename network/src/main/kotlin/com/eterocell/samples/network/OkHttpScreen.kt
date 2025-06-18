package com.eterocell.samples.network

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OkHttpScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        Box(modifier = Modifier.padding(innerPadding)) {
            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        OkHttpTestClient.sendPostRequest()
                    }
                },
            ) { Text("Send Network Request") }
        }
    }
}
