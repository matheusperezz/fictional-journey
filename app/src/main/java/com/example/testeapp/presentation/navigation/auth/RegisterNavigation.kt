package com.example.testeapp.presentation.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testeapp.presentation.screens.auth.RegisterScreen

const val registerRoute = "register"

fun NavGraphBuilder.registerScreen(navHostController: NavHostController){
    composable(registerRoute){
        RegisterScreen(navHostController = navHostController)
    }
}

fun NavHostController.navigateToRegisterScreen(navOptions: NavOptions? = null){
    navigate(registerRoute, navOptions)
}