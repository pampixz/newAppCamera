package com.example.myapplication.data

import android.graphics.Bitmap
import com.example.myapplication.domain.model.RecognitionResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class MLKitAnalyzer {
    fun analyzeImage(bitmap: Bitmap, onResult: (List<RecognitionResult>) -> Unit) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        labeler.process(image)
            .addOnSuccessListener { labels ->
                val results = labels.map {
                    RecognitionResult(it.text, it.confidence)
                }
                onResult(results)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}