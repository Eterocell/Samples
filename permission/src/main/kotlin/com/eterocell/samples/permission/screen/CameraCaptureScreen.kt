package com.eterocell.samples.permission.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.eterocell.samples.permission.LocalNavController

@Composable
fun CameraCaptureScreen() {
    val context = LocalContext.current
    val navController = LocalNavController.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var imageUriToSave by remember { mutableStateOf<Uri?>(null) }

    // SAF 文件创建器
    val createDocumentLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument("image/jpeg"),
        ) { uri ->
            if (uri != null) {
                imageUriToSave = uri
            }
        }

    // 实际拍照逻辑
    fun takePhotoTo(uri: Uri) {
        val capture = imageCapture ?: return
        val outputOptions =
            ImageCapture.OutputFileOptions
                .Builder(
                    context.contentResolver.openOutputStream(uri)!!,
                ).build()

        capture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(context, "已保存", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "保存失败：${exception.message}", Toast.LENGTH_SHORT).show()
                }
            },
        )
    }

    // 相机预览与控制
    Box(Modifier.fillMaxSize()) {
        CameraPreview(
            onImageCaptureCreated = { capture -> imageCapture = capture },
        )

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        ) {
            Button(onClick = {
                // 发起 SAF 选择保存位置
                createDocumentLauncher.launch("IMG_${System.currentTimeMillis()}.jpg")
            }) {
                Text("选择保存位置并拍照")
            }
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier =
                Modifier
                    .align(Alignment.TopStart),
        ) {
            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "返回")
        }
    }

    // 选择位置后立即拍照
    LaunchedEffect(imageUriToSave) {
        imageUriToSave?.let {
            takePhotoTo(it)
            imageUriToSave = null // 重置
        }
    }
}

@Composable
private fun CameraPreview(onImageCaptureCreated: (ImageCapture) -> Unit) {
    val preview = remember { Preview.Builder().build() }
    val capture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { context ->
            val view = PreviewView(context)
            preview.surfaceProvider = view.surfaceProvider
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, capture)
            }, ContextCompat.getMainExecutor(context))
            view
        },
        modifier = Modifier.fillMaxSize(),
    )

    LaunchedEffect(Unit) {
        onImageCaptureCreated(capture)
    }
}
