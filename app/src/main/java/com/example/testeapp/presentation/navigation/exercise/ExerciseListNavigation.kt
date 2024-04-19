package com.example.testeapp.presentation.navigation.exercise

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.exercises.ExerciseListScreen

const val exerciseListRoute = "exerciseList"

fun NavGraphBuilder.exerciseListScreen(navHostController: NavHostController) {
  composable(exerciseListRoute) {
    ExerciseListScreen(onExerciseClick = { exercise ->
      navHostController.navigateToExerciseDetails(
        id = exercise.id
      )
    })
  }
}

fun NavHostController.navigateToExerciseList(navOptions: NavOptions? = null) {
  navigate(exerciseListRoute, navOptions)
}