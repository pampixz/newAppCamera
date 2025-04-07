package com.example.myapplication.presentation.screens

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.media.Image
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.google.accompanist.permissions.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.nio.ByteBuffer

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavHostController, vm: MainViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (cameraPermissionState.status.isGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraPreview(
                lifecycleOwner = lifecycleOwner,
                imageCapture = imageCapture
            )

            // Кнопка "Сделать фото"
            Button(
                onClick = {
                    captureImage(imageCapture, cameraExecutor) { bitmap ->
                        imageBitmap = bitmap
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
            ) {
                Text("Сделать фото")
            }

            // Отображение снимка и кнопка "Распознать"
            imageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 160.dp)
                )

                Button(
                    onClick = {
                        vm.analyze(bitmap)
                        navController.navigate("result")
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Text("Распознать")
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Требуется разрешение на использование камеры")
        }
    }
}

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    imageCapture: ImageCapture
) {
    val context = LocalContext.current
    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    ) { view ->
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(view.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}

fun captureImage(
    imageCapture: ImageCapture,
    executor: ExecutorService,
    onImageCaptured: (Bitmap) -> Unit
) {
    imageCapture.takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                val bitmap = imageProxyToBitmap(imageProxy)
                imageProxy.close()
                bitmap?.let { onImageCaptured(it) }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraCapture", "Image capture failed", exception)
            }
        }
    )
}

// Функция для преобразования ImageProxy в Bitmap
fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
    val image: Image? = imageProxy.image
    if (image == null) {
        imageProxy.close()
        return null
    }

    // Получаем первый план (обычно это YUV)
    val planeProxy = image.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())

    buffer.get(bytes)

    // Преобразуем байты в Bitmap
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    // Закрываем ImageProxy после завершения работы
    imageProxy.close()

    return bitmap
}
