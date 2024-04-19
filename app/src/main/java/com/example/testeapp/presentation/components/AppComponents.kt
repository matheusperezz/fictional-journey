package com.example.testeapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.navigation.TesteAppNavHost
import com.example.testeapp.presentation.navigation.exercise.navigateToCreateExercise

@Composable
fun TesteApp(
  navController: NavHostController,
  currentDestination: NavDestination?,
) {
  // TODO: Implementar lógica para mudar a navegação dependendo da tab selecionada
  Scaffold(
    bottomBar = {
      BottomBarNavigation(
        currentDestination = currentDestination,
        navHostController = navController
      )
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(onClick = { navController.navigateToCreateExercise() }) {
        Text(text = "Criar Exercício")
        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
      }
    }
  ) {
    Box(modifier = Modifier.padding(it)) {
      TesteAppNavHost(navController = navController)
    }
  }
}