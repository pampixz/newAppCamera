package com.example.myapplication.presentation.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.media.Image
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.camera.core.ImageProxy

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavHostController, vm: MainViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
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

            CameraFAB(
                onClick = {
                    captureImage(
                        context = context,
                        imageCapture = imageCapture,
                        executor = cameraExecutor,
                        onSuccess = { bitmap ->
                            vm.saveCapturedImage(bitmap)
                            navController.navigate("preview")
                        },
                        onError = { error ->
                            Log.e("CameraScreen", "Capture failed", error)
                            println("CameraScreen $error")
                        }
                    )
                },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Требуется доступ к камере",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { cameraPermissionState.launchPermissionRequest() }
                ) {
                    Text("Предоставить доступ")
                }
            }
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
    context: Context,
    imageCapture: ImageCapture,
    executor: ExecutorService,
    onSuccess: (Bitmap) -> Unit,
    onError: (Exception) -> Unit = { Log.e("Camera", "Error", it) }
) {
    imageCapture.takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                try {
                    val bitmap = imageProxy.toBitmap(context)
                    ContextCompat.getMainExecutor(context).execute {
                        onSuccess(bitmap)
                    }
                } catch (e: Exception) {
                    ContextCompat.getMainExecutor(context).execute {
                        onError(e)
                    }
                } finally {
                    imageProxy.close()
                }
            }

            override fun onError(exception: ImageCaptureException) {
                ContextCompat.getMainExecutor(context).execute {
                    onError(exception)
                }
            }
        }
    )
}

fun ImageProxy.toBitmap(context: Context): Bitmap {
    val mediaImage = this.image ?: throw IllegalStateException("Image is null")
    val bitmap = yuv420ToBitmap(mediaImage)
    val rotationDegrees = this.imageInfo.rotationDegrees
    this.close()
    return rotateBitmap(bitmap, rotationDegrees)
}

fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Int): Bitmap {
    if (rotationDegrees == 0) return bitmap

    val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
    return Bitmap.createBitmap(
        bitmap,
        0,
        0,
        bitmap.width,
        bitmap.height,
        matrix,
        true
    )
}







fun yuv420ToBitmap(image: Image): Bitmap {
    return when (image.format) {
        ImageFormat.YUV_420_888 -> {
            if (image.planes.size < 3) {
                throw IllegalStateException("YUV_420_888 image does not have 3 planes")
            }

            val yBuffer = image.planes[0].buffer
            val uBuffer = image.planes[1].buffer
            val vBuffer = image.planes[2].buffer

            val ySize = yBuffer.remaining()
            val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()

            val nv21 = ByteArray(ySize + uSize + vSize)
            yBuffer.get(nv21, 0, ySize)
            vBuffer.get(nv21, ySize, vSize)
            uBuffer.get(nv21, ySize + vSize, uSize)

            val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
            val imageBytes = out.toByteArray()
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        ImageFormat.JPEG -> {
            val buffer: ByteBuffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        else -> {
            throw UnsupportedOperationException("Unsupported image format: ${image.format}")
        }
    }
}



@Composable
fun CameraFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(
                width = 4.dp,
                color = Color.White.copy(alpha = 0.8f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 2.dp,
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = CircleShape
                )
        )
    }
}