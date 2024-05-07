package com.example.onerepmax.data.repository

import com.example.onerepmax.data.local.FileReader
import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.parser.WorkoutFileParser
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class FileReadException(message: String): Exception(message)
class DataParseException(message: String): Exception(message)

class ExerciseLocalTxtRepositoryImpl @Inject constructor(private val fileReader: FileReader) : ExerciseRepository {

    private val parser = WorkoutFileParser()
    private val exercisesCache = mutableListOf<WorkoutDTO>()

    override fun getAllExercises(): List<WorkoutDTO> {
        try {
            if (exercisesCache.isEmpty()) {
                val parsedWorkouts = getWorkouts()
                exercisesCache.addAll(parsedWorkouts)
                return parsedWorkouts
            } else {
                return exercisesCache
            }
        } catch (e: FileNotFoundException) {
            throw FileReadException("Workout data file not found: ${e.message}")
        } catch (e: DataParseException) {
            throw DataParseException("Error reading from the workout data file: ${e.message}")
        } catch (e: Exception) {
            throw Exception("An unexpected error occurred: ${e.message}")
        }
    }

    private fun getWorkouts(): List<WorkoutDTO> {
        val lines = fileReader.readLines("workout_data.txt")
        val parsedWorkouts = try {
            parser.parseWorkouts(lines)
        } catch (e: Exception) {
            throw DataParseException("Error parsing workouts from file: ${e.message}")
        }
        return parsedWorkouts
    }

    override fun getExercisesByName(name: String): List<WorkoutDTO> {
        val allExercises = getAllExercises()
        return allExercises.filter { it.exerciseName == name }
    }
}