package com.example.testeapp.presentation.navigation.training

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.training.CreateTrainingSreen

const val createTrainingRoute = "createTraining"

fun NavGraphBuilder.createTrainingScreen(navHostController: NavHostController) {
  composable(createTrainingRoute) {
    CreateTrainingSreen()
  }
}

fun NavController.navigateToCreateTraining(navOptions: NavOptions? = null) {
  navigate(trainingListRoute, navOptions)
}