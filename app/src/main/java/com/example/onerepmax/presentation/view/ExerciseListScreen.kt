package com.example.onerepmax.presentation.view

import androidx.compose.foundation.background
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
import com.example.onerepmax.domain.entity.Exercise
@Composable
fun ExerciseListScreen(exercises : List<Exercise>){
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        items(exercises.size){
            ExerciseItem(exercises[it])
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp,
                horizontal = 16.dp
            )

    ){
        ExerciseInfo(exercise.name, exercise.oneRepMax)
        Spacer(modifier = Modifier.size(4.dp))
        InfoLegend()
        Spacer(modifier = Modifier.height(4.dp))
        Divider(color = Color.LightGray)
    }
}

@Composable
fun ExerciseInfo(name: String, record : Double) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = record.toInt().toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary)
    }
}


@Composable
fun InfoLegend() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "1 RM Record",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.tertiary
        )

        Text(
            text = "lbs",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.tertiary)
    }
}