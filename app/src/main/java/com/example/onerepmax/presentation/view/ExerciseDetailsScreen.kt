package com.example.onerepmax.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onerepmax.domain.entity.ExerciseDetails
import com.example.onerepmax.domain.entity.ExerciseMaxRepRecord
import com.example.onerepmax.presentation.view.charts.LineChart
import com.example.onerepmax.presentation.viewmodels.ExerciseDetailsViewModel

@Composable
fun ExerciseGraphScreen (
    exerciseName : String,
    viewModel: ExerciseDetailsViewModel = hiltViewModel()
) {

    val details by viewModel.details.collectAsState()
    viewModel.getExerciseDetails(exerciseName)

    if (details != ExerciseDetails.empty) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            ExerciseRecordCard(exercise = ExerciseMaxRepRecord(details.name, details.maxRepRecord))
            LineChart(details.historical)
        }
    }
}

