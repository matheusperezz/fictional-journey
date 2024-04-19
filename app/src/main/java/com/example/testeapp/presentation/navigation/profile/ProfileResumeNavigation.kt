package com.example.testeapp.presentation.navigation.profile

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.navigation.training.trainingListRoute

const val profileResumeRoute = "profileResume"

fun NavGraphBuilder.profileResumeScreen(navHostController: NavHostController) {
  composable(profileResumeRoute) {
    Text(text = "Profile List")
  }
}

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
  navigate(profileResumeRoute, navOptions)
}