package com.example.myapplication.data

import android.graphics.Bitmap
import com.example.myapplication.domain.model.RecognitionResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.util.Date

class MLKitAnalyzer {
    fun analyzeImage(bitmap: Bitmap, onResult: (List<RecognitionResult>) -> Unit) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        labeler.process(image)
            .addOnSuccessListener { labels ->
                val now = Date()
                val results = labels.map {
                    RecognitionResult(label = it.text, confidence =  it.confidence,timetamp = now)
                }
                onResult(results)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}