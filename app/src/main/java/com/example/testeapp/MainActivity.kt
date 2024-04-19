package com.example.testeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.presentation.components.BottomBarNavigation
import com.example.testeapp.presentation.components.TesteApp
import com.example.testeapp.presentation.navigation.TesteAppNavHost
import com.example.testeapp.presentation.theme.TesteAppTheme
import com.example.testeapp.presentation.viewmodels.TrainingUiState
import com.example.testeapp.presentation.viewmodels.TrainingViewModel
import com.example.testeapp.utils.TAG
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint

// TODO: Separar as viewmodels durante o formulário de criação de treino
// TODO: Criar um formulário de criação de treino com lista de exercícios
// TODO: Criar um formulário de criação de exercício como bottomFragment
// TODO: Talvez tela de login?
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

