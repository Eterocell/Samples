package com.eterocell.samples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eterocell.samples.leak.JumpToLeakyActivityScreen
import com.eterocell.samples.network.OkHttpScreen
import com.eterocell.samples.permission.PermissionHost
import com.eterocell.samples.ui.theme.SamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SamplesTheme {
//                PermissionHost()
//                JumpToLeakyActivityScreen()
                OkHttpScreen()
            }
        }
    }
}
