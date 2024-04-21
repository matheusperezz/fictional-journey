package com.example.testeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testeapp.presentation.components.TesteApp
import com.example.testeapp.presentation.theme.TesteAppTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO: Implementar telas de login
// TODO: Adicionar fluxo de login
// TODO: Implementar o fluxo de login com o firebase

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TesteAppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        TesteApp(
          navController = navController,
          currentDestination = currentDestination
        )
      }
    }
  }
}

