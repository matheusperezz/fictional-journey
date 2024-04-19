package com.example.testeapp.presentation.navigation.exercise

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.exercises.CreateExerciseScreen
import com.example.testeapp.presentation.screens.exercises.ExerciseListScreen

const val createExerciseRoute = "createExercise"

fun NavGraphBuilder.createExerciseScreen(navHostController: NavHostController) {
  composable(createExerciseRoute) {
    CreateExerciseScreen(navHostController = navHostController)
  }
}

fun NavHostController.navigateToCreateExercise(navOptions: NavOptions? = null) {
  navigate(createExerciseRoute, navOptions)
}