package com.example.onerepmax.domain.entity

data class ExerciseDetails (
    val name: String,
    val maxOneRepRecord: Double,
    val historical : List<HistoricalMaxOneRepRecord>) {
    companion object {
        val empty = ExerciseDetails("", 0.0, emptyList())
    }
}