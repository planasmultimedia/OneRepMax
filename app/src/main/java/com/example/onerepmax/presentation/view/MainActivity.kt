package com.example.onerepmax.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onerepmax.presentation.navigation.AppNavigation
import com.example.onerepmax.presentation.theme.OneRepMaxTheme
import com.example.onerepmax.presentation.view.screens.ExerciseListScreen
import com.example.onerepmax.presentation.viewmodels.ExercisesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneRepMaxTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    AppNavigation()
                }
            }
        }
    }
}


@Composable
fun MainScreen(
    onExerciseClicked : (String) -> Unit,
    viewModel: ExercisesViewModel = hiltViewModel()
) {
    val workouts by viewModel.exercises.collectAsState()
    val error by viewModel.error.collectAsState()

    if (error != null) {
        Text(
            text = error.toString(),
            color = MaterialTheme.colorScheme.primary
        )
    }
    else if (workouts.isNotEmpty()) {
        ExerciseListScreen(exercises = workouts, onExerciseClicked)
    }
    else {
        Text(
            text = "No exercises found",
            color = MaterialTheme.colorScheme.primary
        )
    }
}