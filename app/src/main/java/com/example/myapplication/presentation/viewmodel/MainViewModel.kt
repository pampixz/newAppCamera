package com.example.myapplication.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.MLKitAnalyzer
import com.example.myapplication.domain.model.RecognitionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val analyzer = MLKitAnalyzer()

    private val _results = MutableStateFlow<List<RecognitionResult>>(emptyList())
    val results = _results.asStateFlow()

    val history = mutableListOf<List<RecognitionResult>>()

    fun analyze(bitmap: Bitmap) {
        analyzer.analyzeImage(bitmap) { result ->
            _results.value = result
            history.add(result)
        }
    }
}