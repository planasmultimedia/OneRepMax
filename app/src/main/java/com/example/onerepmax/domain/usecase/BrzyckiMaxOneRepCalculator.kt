package com.example.onerepmax.domain.usecase

class BrzyckiMaxOneRepCalculator {
    fun calculate(weight: Double, reps: Int): Double {
        return weight * (36 / (37.0 - reps))
    }
}