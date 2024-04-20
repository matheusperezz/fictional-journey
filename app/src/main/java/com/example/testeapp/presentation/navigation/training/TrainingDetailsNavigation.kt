package com.example.testeapp.presentation.navigation.training

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testeapp.presentation.screens.training.TrainingDetailsScreen

const val trainingDetailsRoute = "trainingDetails"
const val trainingIdArgument = "id"

fun NavGraphBuilder.trainingDetailsScreen(navHostController: NavHostController) {
  composable(
    route = "$trainingDetailsRoute/{$trainingIdArgument}",
    arguments = listOf(
      navArgument(trainingIdArgument) {
        NavType.StringType
        nullable = true
        defaultValue = ""
      }
    )
  ) {
    val trainingId = it.arguments?.getString(trainingIdArgument)
    if (trainingId != null) {
      TrainingDetailsScreen(trainingId = trainingId, navHostController = navHostController)
    } else {
      navHostController.popBackStack()
    }
  }
}

fun NavController.navigateToTrainingDetails(navOptions: NavOptions? = null, id: String = "") {
  if (id == "") {
    navigate(trainingDetailsRoute, navOptions)
  } else {
    navigate("$trainingDetailsRoute/$id", navOptions)
  }
}