package com.example.onerepmax.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onerepmax.presentation.theme.OneRepMaxTheme
import com.example.onerepmax.presentation.viewmodels.ExercisesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneRepMaxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreen(
    viewModel: ExercisesViewModel = hiltViewModel()
) {
    val workouts by viewModel.exercises.collectAsState()

    if (workouts.isNotEmpty()) {
        ExerciseListScreen(exercises = workouts)
    }
}