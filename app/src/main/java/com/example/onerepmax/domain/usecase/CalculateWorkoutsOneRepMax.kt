package com.example.onerepmax.domain.usecase

import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.repository.WorkoutRepository
import com.example.onerepmax.domain.entity.Exercise
import com.example.onerepmax.domain.entity.HistoricalOneRepMax
import javax.inject.Inject

class CalculateWorkoutsOneRepMax @Inject constructor(
    private val workoutRepository: WorkoutRepository
)  {
    suspend fun execute(): List<Exercise> {
        val workouts = workoutRepository.getWorkouts()
        val exercises : MutableList<Exercise> = mutableListOf()

        workouts
            .groupBy { it.exerciseName }
            .forEach { (exerciseName, groupedWorkouts) ->
                val maxOneRep = groupedWorkouts.maxOf { workout ->
                    calculateOneRepMax(workout.reps, workout.weight)
                }
                val historicalData = calculateHistoricalData(groupedWorkouts)
                exercises.add(Exercise(exerciseName, maxOneRep, historicalData))
        }

        return exercises
    }

    fun calculateOneRepMax(reps: Int, weight: Double): Double {
        if (reps <= 0) {
            throw IllegalArgumentException("Repetitions must be greater than zero.")
        }
        // Brzycki formula
        return weight / (1.0278 - 0.0278 * reps)
    }

    fun calculateHistoricalData(workouts: List<WorkoutDTO>): List<HistoricalOneRepMax> {
        return workouts
            .groupBy { it.date }
            .map { (date, workouts) ->
                val maxOneRep = workouts.maxOf { workout ->
                    calculateOneRepMax(workout.reps, workout.weight)
                }
                HistoricalOneRepMax(date, maxOneRep)
            }
    }
}
