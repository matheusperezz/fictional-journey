package com.example.testeapp.presentation.navigation.exercise

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.exercises.ExerciseDetailsScreen

const val exerciseDetailsRoute = "exerciseDetails"

fun NavGraphBuilder.exerciseDetailsScreen(navHostController: NavHostController) {
  composable(exerciseDetailsRoute) {
    ExerciseDetailsScreen()
  }
}

fun NavHostController.navigateToExerciseDetails(navOptions: NavOptions? = null) {
  navigate(exerciseDetailsRoute, navOptions)
}