package com.example.testeapp.presentation.navigation.training

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val trainingListRoute = "trainingList"

fun NavGraphBuilder.trainingListScreen(navHostController: NavHostController) {
    composable(trainingListRoute) {
        Text(text = "Training List")
    }
}

fun NavController.navigateToTrainingList(navOptions: NavOptions? = null) {
    navigate(trainingListRoute, navOptions)
}