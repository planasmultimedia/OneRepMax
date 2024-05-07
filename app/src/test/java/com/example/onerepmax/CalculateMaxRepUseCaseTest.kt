package com.example.onerepmax

import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.repository.ExerciseRepository
import com.example.onerepmax.domain.entity.ExerciseMaxRepRecord
import com.example.onerepmax.domain.usecase.BrzyckiMaxOneRepCalculator
import com.example.onerepmax.domain.usecase.CalculateMaxRepUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
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
        assert(result[0] == ExerciseMaxRepRecord("Deadlift", 162.0))
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
        assert(result[0] == ExerciseMaxRepRecord("Deadlift", 162.0))
        assert(result[1] == ExerciseMaxRepRecord("Back Squat", 161.0))
    }


    val multipleDeadliftExercises = listOf(
        WorkoutDTO("Deadlift", "20 Oct 2020", 10, 100.0),
        WorkoutDTO("Deadlift", "23 Oct 2020", 8, 130.0),
        WorkoutDTO("Deadlift", "25 Oct 2020", 6, 140.0),
    )

    val multipleBackSquatExercises = listOf(
        WorkoutDTO("Back Squat", "20 Oct 2020", 10, 100.0),
        WorkoutDTO("Back Squat", "23 Oct 2020", 8, 130.0),
    )
}