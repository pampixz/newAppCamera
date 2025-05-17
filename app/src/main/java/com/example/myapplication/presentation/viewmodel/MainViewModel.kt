package com.example.myapplication.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel : ViewModel() {


    // Добавляем свойство для хранения снимка
    private var _capturedImage by mutableStateOf<Bitmap?>(null)
    val capturedImage: Bitmap? get() = _capturedImage

    fun saveCapturedImage(bitmap: Bitmap) {
        _capturedImage = bitmap
    }
    fun clearImage() {
        _capturedImage = null
    }

    // Состояние для текущих результатов распознавания
    private val _result = MutableStateFlow<List<String>>(emptyList())
    val result: StateFlow<List<String>> get() = _result

    // История распознанных объектов (сортировка или удаление старых записей может быть полезна)
    private val _history = mutableListOf<List<DetectedObject.Label>>()
    val history: List<List<DetectedObject.Label>> get() = _history

    // Метод для анализа изображения
    fun analyze(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        // Настройки детектора
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)  // Режим обработки одного изображения
            .enableMultipleObjects()  // Поддержка нескольких объектов
            .enableClassification()  // Классификация объектов
            .build()

        val detector = ObjectDetection.getClient(options)

        // Обработка изображения
        detector.process(image)
            .addOnSuccessListener { detectedObjects ->
                // Извлекаем все метки для каждого объекта
                val labels = detectedObjects.flatMap { obj ->
                    obj.labels.map { it.text }  // Собираем все текстовые метки
                }

                // Если объекты были распознаны, то добавляем их в результат
                if (labels.isNotEmpty()) {
                    _result.value = labels
                } else {
                    _result.value = listOf("Объекты не распознаны")
                }

                // Сохраняем в историю только уникальные объекты
                _history.add(detectedObjects.mapNotNull { it.labels.firstOrNull() })

                // Логирование для отладки
                Log.d("MainViewModel", "Detected objects: $labels")
            }
            .addOnFailureListener { exception ->
                // Обработка ошибки
                _result.value = listOf("Ошибка распознавания: ${exception.message}")
                Log.e("MainViewModel", "Error during detection: ${exception.message}")
            }
    }
}