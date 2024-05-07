package com.example.onerepmax.presentation.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onerepmax.domain.entity.ExerciseDetails
import com.example.onerepmax.domain.entity.ExerciseMaxOneRepRecord
import com.example.onerepmax.presentation.theme.OneRepMaxTheme
import com.example.onerepmax.presentation.view.components.ExerciseRecordCard
import com.example.onerepmax.presentation.view.components.NavTopBar
import com.example.onerepmax.presentation.view.charts.LineChart
import com.example.onerepmax.presentation.viewmodels.ExerciseDetailsViewModel

@Composable
fun ExerciseGraphScreen (
    exerciseName : String,
    onBackPressedCallback: () -> Unit
) {

    OneRepMaxTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colorScheme.background,
            topBar = {
                NavTopBar(
                    title = exerciseName,
                    canNavigateBack = true,
                    navigateUp = {onBackPressedCallback()}
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                ExerciseDetails(exerciseName = exerciseName)
            }
        }
    }
}

@Composable
fun ExerciseDetails(
    exerciseName : String,
    viewModel : ExerciseDetailsViewModel = hiltViewModel()
) {
    val details by viewModel.details.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(exerciseName) {
        viewModel.getExerciseDetails(exerciseName)
    }

    if (error != null) {
        Text(
            text = error.toString(),
            color = MaterialTheme.colorScheme.primary
        )
    }
    else if (details != ExerciseDetails.empty) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            ExerciseRecordCard(exercise = ExerciseMaxOneRepRecord(details.name, details.maxOneRepRecord))
            LineChart(details.historical)
        }
    }
}

