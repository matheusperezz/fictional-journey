package com.example.testeapp.presentation.navigation.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.profile.ProfileResumeScreen

const val profileResumeRoute = "profileResume"

fun NavGraphBuilder.profileResumeScreen(navHostController: NavHostController) {
  composable(profileResumeRoute) {
    ProfileResumeScreen()
  }
}

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
  navigate(profileResumeRoute, navOptions)
}