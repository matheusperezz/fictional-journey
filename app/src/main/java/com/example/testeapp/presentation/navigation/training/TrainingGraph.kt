package com.example.testeapp.presentation.navigation.training

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation

internal const val trainingGraphRoute = "trainingGraph"

fun NavGraphBuilder.trainingGraph(navHostController: NavHostController) {
  navigation(
    startDestination = trainingListRoute,
    route = trainingGraphRoute
  ) {
    trainingListScreen(navHostController)
    createTrainingScreen(navHostController)
    trainingDetailsScreen(navHostController)
  }
}

fun NavController.navigateToTrainingGraph(navOptions: NavOptions? = null) {
  navigate(trainingGraphRoute, navOptions)
}