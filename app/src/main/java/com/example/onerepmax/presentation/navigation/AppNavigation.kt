package com.example.onerepmax.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onerepmax.presentation.view.ExerciseGraphScreen
import com.example.onerepmax.presentation.view.MainScreen


object Destinations {
    const val EXERCISE_LIST_ROUTE = "mainScreen"
    const val EXERCISE_HISTORIC_ROUTE = "exerciseDetail/{exerciseId}"
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.EXERCISE_LIST_ROUTE
    ) {

        composable(Destinations.EXERCISE_LIST_ROUTE) {
            MainScreen(onExerciseClicked = { exerciseId ->
                navController.navigate("exerciseDetail/$exerciseId")
            })
        }

        composable(Destinations.EXERCISE_HISTORIC_ROUTE) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")

            if (exerciseId == null) {
                navController.navigate("exerciseList") {
                    popUpTo("exerciseList") { inclusive = true }
                }
            }
            else {
                ExerciseGraphScreen(exerciseName = exerciseId)
            }
        }
    }
}