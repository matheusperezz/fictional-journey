package com.example.testeapp.presentation.navigation.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation

internal const val profileGraphRoute = "profileGraph"

fun NavGraphBuilder.profileGraph(navController: NavHostController) {
  navigation(
    startDestination = profileResumeRoute,
    route = profileGraphRoute
  ) {
    profileResumeScreen(navController)
  }
}

fun NavController.navigateToProfileGraph(navOptions: NavOptions? = null) {
  navigate(profileGraphRoute, navOptions)
}