package com.eterocell.samples.permission.screen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.core.content.getSystemService

@Composable
fun LocationPermissionScreen() {
    val context = LocalContext.current
    val locationManager =
        context.getSystemService<LocationManager>()
            ?: throw IllegalStateException("Cannot get LocationManager")

    val finePermission = Manifest.permission.ACCESS_FINE_LOCATION
    val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION

    var hasFine by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                finePermission,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }
    var hasCoarse by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                coarsePermission,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    var dialogMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val fineLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { granted ->
            hasFine = granted
            dialogMessage = if (granted) "已授予精确定位权限" else "拒绝了精确定位权限"
            showDialog = true
        }

    val coarseLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { granted ->
            hasCoarse = granted
            dialogMessage = if (granted) "已授予粗略定位权限" else "拒绝了粗略定位权限"
            showDialog = true
        }

    fun fetchLastKnownLocation() {
        val provider =
            when {
                hasFine && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                    LocationManager.GPS_PROVIDER
                hasCoarse && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                    LocationManager.NETWORK_PROVIDER
                else -> null
            }

        if (provider == null) {
            dialogMessage = "无可用的定位服务或未授权"
        } else {
            val location: Location? = locationManager.getLastKnownLocation(provider)
            dialogMessage = location?.let {
                "当前位置:\n纬度: ${it.latitude}\n经度: ${it.longitude}"
            } ?: "无法获取位置（可能尚未更新）"
        }
        showDialog = true
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("定位权限请求与位置获取", style = MaterialTheme.typography.headlineSmall)

        Text("权限状态:", style = MaterialTheme.typography.bodyMedium)
        Text(
            when {
                hasFine -> "✅ 精确定位权限已授权"
                hasCoarse -> "🟡 仅有粗略定位权限"
                else -> "❌ 无定位权限"
            },
            style = MaterialTheme.typography.bodyLarge,
        )

        Button(
            onClick = {
                if (!hasFine) {
                    fineLauncher.launch(finePermission)
                } else {
                    dialogMessage = "已拥有精确定位权限"
                    showDialog = true
                }
            },
        ) {
            Text("请求精确定位权限")
        }

        Button(
            onClick = {
                if (!hasCoarse) {
                    coarseLauncher.launch(coarsePermission)
                } else {
                    dialogMessage = "已拥有粗略定位权限"
                    showDialog = true
                }
            },
        ) {
            Text("请求粗略定位权限")
        }

        Button(
            onClick = {
                if (hasFine || hasCoarse) {
                    fetchLastKnownLocation()
                } else {
                    dialogMessage = "请先授权定位权限"
                    showDialog = true
                }
            },
            enabled = hasFine || hasCoarse,
        ) {
            Text("获取当前位置")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("提示") },
            text = { Text(dialogMessage) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("确定")
                }
            },
        )
    }
}
