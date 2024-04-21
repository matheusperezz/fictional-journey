package com.example.testeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.testeapp.presentation.navigation.auth.authGraph
import com.example.testeapp.presentation.navigation.auth.authGraphRoute

@Composable
fun TesteAppNavHost(navController: NavHostController) {
  NavHost(navController = navController, startDestination = authGraphRoute) {
    mainGraph(navController)
    authGraph(navController)
  }
}