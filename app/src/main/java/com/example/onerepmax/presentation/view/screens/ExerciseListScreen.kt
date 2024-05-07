package com.example.onerepmax.presentation.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.onerepmax.R
import com.example.onerepmax.domain.entity.ExerciseMaxRepRecord
import com.example.onerepmax.presentation.theme.OneRepMaxTheme
import com.example.onerepmax.presentation.view.components.ExerciseRecordCard
import com.example.onerepmax.presentation.view.components.NavTopBar

@Composable
fun ExerciseListScreen(
    exercises : List<ExerciseMaxRepRecord>,
    onExerciseClicked : (String) -> Unit,
) {
    OneRepMaxTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colorScheme.background,
            topBar = {
                NavTopBar(
                    title = stringResource(R.string.home_title),
                    canNavigateBack = false
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                ExerciseList(exercises, onExerciseClicked)
            }
        }
    }
}

@Composable
fun ExerciseList(exercises: List<ExerciseMaxRepRecord>, onExerciseClicked : (String) -> Unit) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        items(exercises.size){
            ExerciseItem(exercises[it], onExerciseClicked)
        }
    }
}

@Composable
fun ExerciseItem(exercise: ExerciseMaxRepRecord, onExerciseClicked : (String) -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp,
                horizontal = 16.dp
            )
            .clickable { onExerciseClicked(exercise.name) }
    ) {
        ExerciseRecordCard(exercise = exercise)
        Divider(color = Color.LightGray)
    }
}
