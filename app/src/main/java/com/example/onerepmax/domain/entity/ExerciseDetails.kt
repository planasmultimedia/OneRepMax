package com.example.onerepmax.domain.entity

data class ExerciseDetails (
    val name: String,
    val maxRepRecord: Double,
    val historical : List<HistoricalMaxRepRecord>) {
    companion object {
        val empty = ExerciseDetails("", 0.0, emptyList())
    }
}