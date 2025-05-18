package com.example.myapplication.domain.model

import java.util.Date

data class RecognitionResult(
    val label: String,
    val confidence: Float, //точность сканирования
    val timetamp: Date     //дата и время сканирования
)