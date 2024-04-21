package com.example.testeapp.presentation.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.auth.LoginScreen

const val loginRoute = "login"

fun NavGraphBuilder.loginScreen(navHostController: NavHostController) {
    composable(loginRoute) {
        LoginScreen(navHostController = navHostController)
    }
}

fun NavHostController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(loginRoute, navOptions)
}