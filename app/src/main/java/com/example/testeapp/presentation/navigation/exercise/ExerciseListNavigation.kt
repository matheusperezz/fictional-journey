package com.example.testeapp.presentation.navigation.exercise

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val exerciseListRoute = "exerciseList"

fun NavGraphBuilder.exerciseListScreen(navHostController: NavHostController) {
    composable(exerciseListRoute) {
        Text(text = "Exercise List")
    }
}

fun NavHostController.navigateToExerciseList(navOptions: NavOptions? = null) {
    navigate(exerciseListRoute, navOptions)
}