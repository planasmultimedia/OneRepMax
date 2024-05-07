package com.example.onerepmax.presentation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.onerepmax.domain.entity.ExerciseMaxRepRecord

@Composable
fun ExerciseRecordCard(exercise : ExerciseMaxRepRecord) {
    ExerciseInfo(exercise.name, exercise.maxRep)
    Spacer(modifier = Modifier.size(4.dp))
    InfoLegend()
    Spacer(modifier = Modifier.height(4.dp))
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