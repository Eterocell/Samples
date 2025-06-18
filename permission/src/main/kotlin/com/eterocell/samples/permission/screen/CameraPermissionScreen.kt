package com.eterocell.samples.permission.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.eterocell.samples.permission.LocalNavController
import com.eterocell.samples.permission.PermissionScreens

@Composable
fun CameraPermissionScreen() {
    val context = LocalContext.current
    val navController = LocalNavController.current
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.all { it.value }
        dialogMessage = if (allGranted) {
            "所有权限已授予"
        } else {
            "权限被拒绝：" + result.filterValues { !it }.keys.joinToString()
        }
    }

    Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("请求相机与存储权限", style = MaterialTheme.typography.headlineSmall)

        Button(onClick = { launcher.launch(permissions) }) {
            Text("请求权限")
        }

        Button(onClick = {
            val granted = permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
            if (granted) navController.navigate(PermissionScreens.CameraCapture.route)
            else dialogMessage = "请先授予所有权限"
        }) {
            Text("进入拍照页面")
        }
    }

    if (dialogMessage != null) {
        AlertDialog(
            onDismissRequest = { dialogMessage = null },
            title = { Text("权限状态") },
            text = { Text(dialogMessage!!) },
            confirmButton = {
                TextButton(onClick = { dialogMessage = null }) {
                    Text("确定")
                }
            }
        )
    }
}
