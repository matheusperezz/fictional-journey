package com.example.testeapp.presentation.navigation.exercise

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testeapp.presentation.screens.exercises.CreateExerciseScreen
import com.example.testeapp.presentation.screens.exercises.ExerciseListScreen

const val createExerciseRoute = "createExercise"
const val createExerciseIdArgument = "createExerciseIdArg"

fun NavGraphBuilder.createExerciseScreen(navHostController: NavHostController) {
  composable(createExerciseRoute) {
    CreateExerciseScreen(navHostController = navHostController)
  }
  composable(
    route = "$createExerciseRoute/{$createExerciseIdArgument}",
    arguments = listOf(
      navArgument(createExerciseIdArgument){
        NavType.StringType
        nullable = true
        defaultValue = ""
      }
    )
  ){ navBackStackEntry ->
    val id = navBackStackEntry.arguments?.getString(createExerciseIdArgument)
    if (id != null){
      CreateExerciseScreen(navHostController = navHostController, exerciseId = id)
    } else {
      navHostController.popBackStack()
    }
  }
}

fun NavHostController.navigateToCreateExercise(navOptions: NavOptions? = null) {
  navigate(createExerciseRoute, navOptions)
}

fun NavHostController.navigateToUpdateExercise(navOptions: NavOptions? = null, id: String = ""){
  navigate("$createExerciseRoute/$id", navOptions)
}