package com.example.onerepmax.data.parser

import com.example.onerepmax.data.model.WorkoutDTO

class WorkoutFileParser {
    fun parseWorkouts(lines: List<String>): List<WorkoutDTO> {
        return lines.filter { it.isNotEmpty() }.map { parseLine(it) }
    }

    private fun parseLine(line: String): WorkoutDTO {
        val parts = line.split(",")
        return WorkoutDTO(
            date = parts[0].trim(),
            exerciseName = parts[1].trim(),
            reps = parts[2].trim().toInt(),
            weight = parts[3].trim().toDouble()
        )
    }
}