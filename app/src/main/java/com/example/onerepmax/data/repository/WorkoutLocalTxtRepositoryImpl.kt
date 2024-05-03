package com.example.onerepmax.data.repository

import com.example.onerepmax.data.local.AssetFileReader
import com.example.onerepmax.data.local.FileReader
import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.parser.WorkoutFileParser
import javax.inject.Inject

class WorkoutLocalTxtRepositoryImpl @Inject constructor(private val fileReader: FileReader) : WorkoutRepository {

    private val parser = WorkoutFileParser()
    override fun getWorkouts(): List<WorkoutDTO> {
        val lines = fileReader.readLines("workout_data.txt")
        return parser.parseWorkouts(lines)
    }
}