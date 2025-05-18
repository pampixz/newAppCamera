package com.example.myapplication.presentation.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.MLKitAnalyzer
import com.example.myapplication.domain.model.RecognitionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val analyzer = MLKitAnalyzer()

    private var _capturedImage = BitmapHolder(null)
    val capturedImage: Bitmap? get() = _capturedImage.bitmap

    fun saveCapturedImage(bitmap: Bitmap) {
        _capturedImage.bitmap = bitmap
    }

    fun clearImage() {
        _capturedImage.bitmap = null
    }

    private val _result = MutableStateFlow<List<RecognitionResult>>(emptyList())
    val result: StateFlow<List<RecognitionResult>> get() = _result

    private val _history = MutableStateFlow<List<RecognitionResult>>(emptyList())
    val history: StateFlow<List<RecognitionResult>> get() = _history


    fun analyze(bitmap: Bitmap) {
        analyzer.analyzeImage(bitmap) { results ->
            if (results.isNotEmpty()) {
                _result.value = results
                _history.value = _history.value + results // добавляем новые результаты к старым
            } else {
                _result.value = listOf(
                    RecognitionResult("Объекты не распознаны", 0f, java.util.Date())
                )
            }

            Log.d("MainViewModel", "Detected: $results")
        }
    }


    // Обертка, чтобы обойти ограничение var внутри ViewModel
    private class BitmapHolder(var bitmap: Bitmap?)
}

