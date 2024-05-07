package com.example.onerepmax.data.repository

import com.example.onerepmax.data.model.WorkoutDTO

interface ExerciseRepository {
    fun getAllExercises() : List<WorkoutDTO>
    fun getExercisesByName(id: String) : List<WorkoutDTO>
}