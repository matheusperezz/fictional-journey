package com.example.testeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun TesteAppNavHost(navController: NavHostController) {
  NavHost(navController = navController, startDestination = mainGraphRoute) {
    mainGraph(navController)
  }
}