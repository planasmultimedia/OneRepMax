package com.example.onerepmax

import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.repository.ExerciseRepository
import com.example.onerepmax.domain.entity.ExerciseMaxRepRecord
import com.example.onerepmax.domain.entity.HistoricalMaxRepRecord
import com.example.onerepmax.domain.usecase.BrzyckiMaxOneRepCalculator
import com.example.onerepmax.domain.usecase.FetchExerciseHistoryDetailsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchExerciseHistoryDetailsUseCaseTest {

    @RelaxedMockK
    private lateinit var exerciseRepository: ExerciseRepository

    @RelaxedMockK
    private lateinit var calculator: BrzyckiMaxOneRepCalculator

    private lateinit var useCase: FetchExerciseHistoryDetailsUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCase = FetchExerciseHistoryDetailsUseCase(exerciseRepository, calculator)
    }

    @Test
    fun `when the repository return correct but not ordered list return an ordered list` () = runBlocking {
        coEvery { exerciseRepository.getExercisesByName("Deadlift") } returns multipleNotOrderedDeadliftExercises
        every { calculator.calculate(100.0, 10) } returns 138.0
        every { calculator.calculate(130.0, 8) } returns 161.0
        every { calculator.calculate(140.0, 6) } returns 162.0

        val result = useCase.execute("Deadlift")

        coVerify(exactly = 1) { exerciseRepository.getExercisesByName("Deadlift") }
        assert(result.historical.size == 4)
        assert(result.historical[0] == HistoricalMaxRepRecord("Oct 17 2020", 161.0))
        assert(result.historical[1] == HistoricalMaxRepRecord("Oct 18 2020", 161.0))
        assert(result.historical[2] == HistoricalMaxRepRecord("Oct 20 2020", 138.0))
        assert(result.historical[3] == HistoricalMaxRepRecord("Oct 27 2020", 162.0))
        assert(result.maxRepRecord == 162.0)
        assert(result.name.equals("Deadlift"))
    }

    @Test
    fun `when the repository return correct list with multiple exercises the same day return the best one rep max` () = runBlocking {
        coEvery { exerciseRepository.getExercisesByName("Deadlift") } returns multipleNotOrderedDeadliftExercisesInOneDay
        every { calculator.calculate(100.0, 10) } returns 138.0
        every { calculator.calculate(130.0, 8) } returns 161.0
        every { calculator.calculate(140.0, 6) } returns 162.0

        val result = useCase.execute("Deadlift")

        coVerify(exactly = 1) { exerciseRepository.getExercisesByName("Deadlift") }
        assert(result.historical.size == 3)
        assert(result.historical[0] == HistoricalMaxRepRecord("Oct 17 2020", 161.0))
        assert(result.historical[1] == HistoricalMaxRepRecord("Oct 18 2020", 161.0))
        assert(result.historical[2] == HistoricalMaxRepRecord("Oct 27 2020", 162.0))
        assert(result.maxRepRecord == 162.0)
        assert(result.name.equals("Deadlift"))
    }

    val multipleNotOrderedDeadliftExercises = listOf(
        WorkoutDTO("Deadlift", "Oct 20 2020", 10, 100.0),
        WorkoutDTO("Deadlift", "Oct 18 2020", 8, 130.0),
        WorkoutDTO("Deadlift", "Oct 27 2020", 6, 140.0),
        WorkoutDTO("Deadlift", "Oct 17 2020", 8, 130.0),
    )

    val multipleNotOrderedDeadliftExercisesInOneDay = listOf(
        WorkoutDTO("Deadlift", "Oct 27 2020", 10, 100.0),
        WorkoutDTO("Deadlift", "Oct 18 2020", 8, 130.0),
        WorkoutDTO("Deadlift", "Oct 27 2020", 6, 140.0),
        WorkoutDTO("Deadlift", "Oct 17 2020", 8, 130.0),
    )
}