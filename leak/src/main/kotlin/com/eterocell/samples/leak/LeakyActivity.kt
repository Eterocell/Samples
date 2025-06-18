package com.eterocell.samples.leak

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

class LeakyActivity : ComponentActivity() {
    private val handler =
        Handler(Looper.getMainLooper()) {
            Toast.makeText(this, "Still here", Toast.LENGTH_SHORT).show()
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler.postDelayed({}, 60_000) // 延迟 1 分钟，期间 Activity 无法回收

        setContent {
            Text("This leaks via Handler")
        }
    }
}

@Composable
fun JumpToLeakyActivityScreen() {
    val currentActivity =
        LocalActivity.current ?: throw IllegalStateException("Cannot get currentActivity")

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text("LeakCanary Demo", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    startActivity(
                        currentActivity,
                        Intent(currentActivity, LeakyActivity::class.java),
                        null,
                    )
                },
            ) {
                Text("Go to LeakyActivity")
            }
        }
    }
}
