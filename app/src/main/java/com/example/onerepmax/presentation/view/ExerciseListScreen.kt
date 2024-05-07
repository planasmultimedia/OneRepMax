package com.example.onerepmax.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.onerepmax.domain.entity.ExerciseMaxRepRecord

@Composable
fun ExerciseListScreen(
    exercises : List<ExerciseMaxRepRecord>,
    onExerciseClicked : (String) -> Unit,
){
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
