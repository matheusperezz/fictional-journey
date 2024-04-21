package com.example.testeapp.presentation.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation

internal const val authGraphRoute = "authGraph"

fun NavGraphBuilder.authGraph(navHostController: NavHostController){
    navigation(
        startDestination = loginRoute,
        route = authGraphRoute
    ){
        loginScreen(navHostController)
        registerScreen(navHostController)
    }
}

fun NavHostController.navigateToAuthGraph(navOptions: NavOptions? = null){
    navigate(authGraphRoute, navOptions)
}