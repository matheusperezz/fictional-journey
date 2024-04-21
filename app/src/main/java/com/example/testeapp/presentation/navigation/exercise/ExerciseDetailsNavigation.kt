package com.example.testeapp.presentation.navigation.exercise

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testeapp.presentation.screens.exercises.ExerciseDetailsScreen

const val exerciseDetailsRoute = "exerciseDetails"
const val exerciseIdArgument = "id"

fun NavGraphBuilder.exerciseDetailsScreen(navHostController: NavHostController) {
  composable(
    route = "$exerciseDetailsRoute/{$exerciseIdArgument}",
    arguments = listOf(
      navArgument(exerciseIdArgument) {
        NavType.StringType
        nullable = true
        defaultValue = ""
      }
    )
  ) { backStackEntry ->
    val exerciseId = backStackEntry.arguments?.getString(exerciseIdArgument)
    if (exerciseId != null) {
      ExerciseDetailsScreen(exerciseId = exerciseId, navHostController = navHostController)
    } else {
      navHostController.popBackStack()
    }
  }
}

fun NavHostController.navigateToExerciseDetails(navOptions: NavOptions? = null, id: String = "") {
  if (id == "") {
    navigate(exerciseDetailsRoute, navOptions)
  } else {
    navigate("$exerciseDetailsRoute/$id", navOptions)
  }
}