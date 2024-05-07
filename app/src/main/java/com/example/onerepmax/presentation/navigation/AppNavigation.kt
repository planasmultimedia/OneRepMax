package com.example.onerepmax.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onerepmax.presentation.view.screens.ExerciseGraphScreen
import com.example.onerepmax.presentation.view.MainScreen


object Destinations {
    const val EXERCISE_LIST_ROUTE = "mainScreen"
    const val EXERCISE_HISTORIC_ROUTE = "exerciseDetail/{exerciseId}"
}
object DestinationParams {
    const val EXERCISE_ID_PARAM = "exerciseId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val appNavigator = remember { AppNavigator(navController) }

    NavHost(navController = navController, startDestination = Destinations.EXERCISE_LIST_ROUTE) {
        composable(Destinations.EXERCISE_LIST_ROUTE) {
            MainScreen(onExerciseClicked = appNavigator::navigateToExerciseDetail)
        }

        composable(Destinations.EXERCISE_HISTORIC_ROUTE) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString(DestinationParams.EXERCISE_ID_PARAM)
            if (exerciseId == null) {
                appNavigator.navigateBackToExerciseList()
            } else {
                ExerciseGraphScreen(
                    exerciseName = exerciseId,
                    onBackPressedCallback = { navController.popBackStack() }
                )
            }
        }
    }
}

class AppNavigator(private val navController: NavController) {
    fun navigateToExerciseDetail(exerciseId: String) {
        navController.navigate("exerciseDetail/$exerciseId")
    }

    fun navigateBackToExerciseList() {
        navController.navigate(Destinations.EXERCISE_LIST_ROUTE) {
            popUpTo(Destinations.EXERCISE_LIST_ROUTE) { inclusive = true }
        }
    }
}