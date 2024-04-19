package com.example.testeapp.presentation.navigation.training

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.training.TrainingDetailsScreen

const val trainingDetailsRoute = "trainingDetails"

fun NavGraphBuilder.trainingDetailsScreen(navHostController: NavHostController) {
  composable(trainingDetailsRoute) {
    TrainingDetailsScreen()
  }
}

fun NavController.navigateToTrainingDetails(navOptions: NavOptions? = null) {
  navigate(trainingListRoute, navOptions)
}