package com.example.onerepmax

import com.example.onerepmax.data.local.FileReader
import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.repository.DataParseException
import com.example.onerepmax.data.repository.ExerciseLocalTxtRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class ExerciseLocalTxtRepositoryImplTest {

    @RelaxedMockK
    private lateinit var fileReader: FileReader

    private lateinit var repository: ExerciseLocalTxtRepositoryImpl

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        repository = ExerciseLocalTxtRepositoryImpl(fileReader)
    }

    @Test
    fun `when the reader return list with one type of exercise return the parsed models` () = runBlocking {
        coEvery { fileReader.readLines("workout_data.txt") } returns deadLiftExercises
        val result = repository.getAllExercises()

        coVerify(exactly = 1) { fileReader.readLines("workout_data.txt") }
        assert(result[0].exerciseName == "Deadlift")
        assert(result[0].reps == 10)
        assert(result[0].weight == 100.0)
        assert(result[0].date == "Oct 20 2020")

        assert(result[1].exerciseName == "Deadlift")
        assert(result[1].reps == 8)
        assert(result[1].weight == 130.0)
        assert(result[1].date == "Oct 23 2020")
    }

    @Test
    fun `when the reader return list with two types of exercise return the parsed models` () = runBlocking {
        coEvery { fileReader.readLines("workout_data.txt") } returns deadLiftExercises + BackSquatExercises
        val result = repository.getAllExercises()

        coVerify(exactly = 1) { fileReader.readLines("workout_data.txt") }
        assert(result[0].exerciseName == "Deadlift")
        assert(result[0].reps == 10)
        assert(result[0].weight == 100.0)
        assert(result[0].date == "Oct 20 2020")

        assert(result[1].exerciseName == "Deadlift")
        assert(result[1].reps == 8)
        assert(result[1].weight == 130.0)
        assert(result[1].date == "Oct 23 2020")

        assert(result[2].exerciseName == "Back Squat")
        assert(result[2].reps == 7)
        assert(result[2].weight == 90.0)
        assert(result[2].date == "Oct 25 2020")

        assert(result[3].exerciseName == "Back Squat")
        assert(result[3].reps == 12)
        assert(result[3].weight == 130.0)
        assert(result[3].date == "Oct 28 2020")
    }

    @Test
    fun `when the reader try to read an invalid date throw a DataParseException` () = runBlocking {
        coEvery { fileReader.readLines("workout_data.txt") } returns invalidExercise

        var exceptionThrown: Boolean = false
        try {
            val result = repository.getAllExercises()
        }
        catch (exception: DataParseException) {
            exceptionThrown = true
        }
        assert(exceptionThrown)
    }

    val deadLiftExercises = listOf(
        "Oct 20 2020, Deadlift, 10, 100",
        "Oct 23 2020, Deadlift, 8, 130"
    )

    val BackSquatExercises = listOf(
        "Oct 25 2020, Back Squat, 7, 90",
        "Oct 28 2020, Back Squat, 12, 130"
    )

    val invalidExercise = listOf(
        "Octtt 25 2020, Back Squat, 7, 90",
    )
}