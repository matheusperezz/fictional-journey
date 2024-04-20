package com.example.testeapp.presentation.navigation.training

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testeapp.presentation.screens.training.CreateTrainingSreen

const val createTrainingRoute = "createTraining"
const val createTrainingIdArgument = "createTrainingIdArg"

fun NavGraphBuilder.createTrainingScreen(navHostController: NavHostController) {
  composable(createTrainingRoute) {
    CreateTrainingSreen(
      navHostController = navHostController
    )
  }
  composable(
    route = "$createTrainingRoute/{$createTrainingIdArgument}",
    arguments = listOf(
      navArgument(createTrainingIdArgument) {
        type = NavType.StringType
        nullable = true
        defaultValue = ""
      }
    )
  ) { navBackStackEntry ->
    val id = navBackStackEntry.arguments?.getString(createTrainingIdArgument)
    if (id != null) {
      CreateTrainingSreen(navHostController = navHostController, trainingId = id)
    } else {
      navHostController.popBackStack()
    }
  }
}

fun NavController.navigateToCreateTraining(navOptions: NavOptions? = null) {
  navigate(createTrainingRoute, navOptions)
}

fun NavController.navigateToUpdateTraining(navOptions: NavOptions? = null, id: String = "") {
  navigate("$createTrainingRoute/$id", navOptions)
}