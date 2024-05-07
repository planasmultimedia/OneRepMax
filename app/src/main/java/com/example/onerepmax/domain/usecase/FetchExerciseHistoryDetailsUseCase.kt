package com.example.onerepmax.domain.usecase

import com.example.onerepmax.data.repository.ExerciseRepository
import com.example.onerepmax.domain.entity.ExerciseDetails
import com.example.onerepmax.domain.entity.HistoricalMaxRepRecord
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class FetchExerciseHistoryDetailsUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val calculator: BrzyckiMaxOneRepCalculator,
)  {
    suspend fun execute(exerciseName : String): ExerciseDetails {
        try {
            val allExercise = exerciseRepository.getExercisesByName(exerciseName)
            val maxReps = allExercise
                .groupBy { it.date }
                .map { (date, exercises) ->
                    val maxOneRep = exercises.maxOf { exercise ->
                        calculator.calculate(exercise.weight, exercise.reps)
                    }
                    HistoricalMaxRepRecord(date, maxOneRep)
                }

            val orderedHistorical = maxReps.sortedBy { parseDate(it.date)}
            val maxRepRecord = orderedHistorical.maxOf { it.maxRep }

            return ExerciseDetails(exerciseName, maxRepRecord, orderedHistorical)
        }
        catch (e: Exception) {
            throw e
        }
    }

    fun parseDate(dateStr: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.US)
        return LocalDate.parse(dateStr, formatter)
    }
}