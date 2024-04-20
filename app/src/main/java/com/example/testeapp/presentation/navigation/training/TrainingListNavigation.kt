package com.example.testeapp.presentation.navigation.training

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.training.TrainingListScreen

const val trainingListRoute = "trainingList"

fun NavGraphBuilder.trainingListScreen(navHostController: NavHostController) {
    composable(trainingListRoute) {
        TrainingListScreen(onTrainingClick = { training ->
            navHostController.navigateToTrainingDetails(
                id = training.id
            )
        })
    }
}

fun NavController.navigateToTrainingList(navOptions: NavOptions? = null) {
    navigate(trainingListRoute, navOptions)
}