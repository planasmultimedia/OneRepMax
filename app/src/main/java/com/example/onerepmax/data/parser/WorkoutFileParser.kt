package com.example.onerepmax.data.parser

import com.example.onerepmax.data.model.WorkoutDTO
import com.example.onerepmax.data.repository.DataParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class WorkoutFileParser {

    fun parseWorkouts(lines: List<String>): List<WorkoutDTO> {
        val workouts = mutableListOf<WorkoutDTO>()
        lines.filter { it.isNotEmpty() }.forEach { line ->
            try {
                workouts.add(parseLine(line))
            } catch (e: Exception) {
                throw Exception("Failed to parse line [$line]: ${e.message}")
            }
        }
        return workouts
    }

    private fun parseLine(line: String): WorkoutDTO {
        val parts = line.split(",")
        val date = parts[0]
        validateDate(date)

        return WorkoutDTO(
            date = date,
            exerciseName = parts[1].trim(),
            reps = parts[2].trim().toInt(),
            weight = parts[3].trim().toDouble()
        )
    }

    private fun validateDate(date: String){
        val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.US)
        try {
            LocalDate.parse(date, formatter).toString()
        } catch (e: DateTimeParseException) {
            throw DataParseException("Invalid date format ${e.message}")
        }
    }
}