package com.example.onerepmax.domain.entity

data class Exercise (
    val name: String,
    val oneRepMax: Double,
    val historical: List<HistoricalOneRepMax>
)
