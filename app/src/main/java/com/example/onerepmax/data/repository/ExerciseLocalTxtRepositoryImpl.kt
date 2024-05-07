package com.example.onerepmax.data.repository

import com.example.onerepmax.data.local.FileReader
import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.parser.WorkoutFileParser
import javax.inject.Inject

class ExerciseLocalTxtRepositoryImpl @Inject constructor(private val fileReader: FileReader) : ExerciseRepository {

    private val parser = WorkoutFileParser()
    private val exercisesCache = mutableListOf<WorkoutDTO>()

    override fun getAllExercises(): List<WorkoutDTO> {
        if (exercisesCache.isEmpty()) {
            val lines = fileReader.readLines("workout_data.txt")
            return parser.parseWorkouts(lines)
        }
        else {
            return exercisesCache
        }
    }

    override fun getExercisesByName(name: String): List<WorkoutDTO> {
        val allExercises = getAllExercises()
        return allExercises.filter { it.exerciseName == name }
    }
}