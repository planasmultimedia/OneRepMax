package com.example.onerepmax

import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.repository.DataParseException
import com.example.onerepmax.data.repository.ExerciseRepository
import com.example.onerepmax.domain.entity.ExerciseMaxOneRepRecord
import com.example.onerepmax.domain.usecase.BrzyckiMaxOneRepCalculator
import com.example.onerepmax.domain.usecase.CalculateMaxRepUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CalculateMaxRepUseCaseTest {

    @RelaxedMockK
    private lateinit var exerciseRepository: ExerciseRepository

    @RelaxedMockK
    private lateinit var calculator: BrzyckiMaxOneRepCalculator

    private lateinit var calculateMaxRepUseCase: CalculateMaxRepUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        calculateMaxRepUseCase = CalculateMaxRepUseCase(exerciseRepository, calculator)
    }

    @Test
    fun `when the repository return an empty list return an empty list` () = runBlocking {
        coEvery { exerciseRepository.getAllExercises() } returns emptyList()
        val result = calculateMaxRepUseCase.execute()

        coVerify(exactly = 1) { exerciseRepository.getAllExercises() }
        assert(result.isEmpty())
    }

    @Test
    fun `when execute return correct max reps for single exercise` () = runBlocking {
        coEvery { exerciseRepository.getAllExercises() } returns multipleDeadliftExercises
        every { calculator.calculate(100.0, 10) } returns 138.0
        every { calculator.calculate(130.0, 8) } returns 161.0
        every { calculator.calculate(140.0, 6) } returns 162.0

        val result = calculateMaxRepUseCase.execute()

        assert(result.size == 1)
        assert(result[0] == ExerciseMaxOneRepRecord("Deadlift", 162.0))
    }


    @Test
    fun `when execute return correct max reps for multiple exercises` () = runBlocking {
        val exercises = multipleDeadliftExercises + multipleBackSquatExercises

        coEvery { exerciseRepository.getAllExercises() } returns exercises

        every { calculator.calculate(100.0, 10) } returns 138.0
        every { calculator.calculate(130.0, 8) } returns 161.0
        every { calculator.calculate(140.0, 6) } returns 162.0

        val result = calculateMaxRepUseCase.execute()

        assert(result.size == 2)
        assert(result[0] == ExerciseMaxOneRepRecord("Deadlift", 162.0))
        assert(result[1] == ExerciseMaxOneRepRecord("Back Squat", 161.0))
    }

    @Test
    fun `when the date format is incorrect throw DataParseException` () = runBlocking {
        coEvery { exerciseRepository.getAllExercises() } throws DataParseException("Invalid date format")
        every { calculator.calculate(100.0, 10) } returns 138.0

        var exceptionThrown = false
        try {
            val result = calculateMaxRepUseCase.execute()
        } catch(exception: DataParseException) {
            exceptionThrown = true
        }
        assertTrue(exceptionThrown)
    }


    val multipleDeadliftExercises = listOf(
        WorkoutDTO("Deadlift", "Oct 20 2020", 10, 100.0),
        WorkoutDTO("Deadlift", "Oct 23 2020", 8, 130.0),
        WorkoutDTO("Deadlift", "Oct 25 2020", 6, 140.0),
    )

    val multipleBackSquatExercises = listOf(
        WorkoutDTO("Back Squat", "Oct 20 2020", 10, 100.0),
        WorkoutDTO("Back Squat", "Oct 23 2020", 8, 130.0),
    )
}