package com.example.onerepmax.data.repository

import com.example.onerepmax.data.model.WorkoutDTO

interface WorkoutRepository {
    fun getWorkouts() : List<WorkoutDTO>
}