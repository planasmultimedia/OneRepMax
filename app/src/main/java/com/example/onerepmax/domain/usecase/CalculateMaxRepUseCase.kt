package com.example.onerepmax.domain.usecase

import com.example.onerepmax.data.repository.ExerciseRepository
import com.example.onerepmax.domain.entity.ExerciseMaxOneRepRecord
import javax.inject.Inject
class CalculateMaxRepUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val calculator: BrzyckiMaxOneRepCalculator
)  {
    suspend fun execute(): List<ExerciseMaxOneRepRecord> {
        try {
            val allExercise = exerciseRepository.getAllExercises()
            return allExercise.groupBy { it.exerciseName }
                .map { (exercise, entries) ->
                    ExerciseMaxOneRepRecord(
                        name = exercise,
                        maxOneRep = entries.maxOf { entry -> calculator.calculate(entry.weight, entry.reps) }
                    )
                }
        }
        catch (e: Exception) {
            throw e
        }
    }
}