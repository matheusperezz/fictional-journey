package com.example.testeapp.presentation.navigation.exercise

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation

internal const val exerciseGraphRoute = "exerciseGraph"

fun NavGraphBuilder.exerciseGraph(navHostController: NavHostController) {
    navigation(
        startDestination = exerciseListRoute,
        route = exerciseGraphRoute
    ) {
        exerciseListScreen(navHostController)
        createExerciseScreen(navHostController)
        exerciseDetailsScreen(navHostController)
    }
}

fun NavController.navigateToExerciseGraph(navOptions: NavOptions? = null) {
    navigate(exerciseGraphRoute, navOptions)
}