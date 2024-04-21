package com.example.testeapp.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.navigation.TesteAppNavHost
import com.example.testeapp.presentation.navigation.exercise.exerciseListRoute
import com.example.testeapp.presentation.navigation.exercise.navigateToCreateExercise
import com.example.testeapp.presentation.navigation.profile.profileResumeRoute
import com.example.testeapp.presentation.navigation.training.createTrainingRoute
import com.example.testeapp.presentation.navigation.training.navigateToCreateTraining
import com.example.testeapp.presentation.navigation.training.trainingDetailsRoute
import com.example.testeapp.presentation.navigation.training.trainingListRoute
import com.example.testeapp.utils.TAG

enum class NavigationType {
  EXERCISE,
  TRAINING,
  PROFILE,
}

@Composable
fun TesteApp(
  navController: NavHostController,
  currentDestination: NavDestination?,
) {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
  ) {

    val currentRoute = currentDestination?.route
    val navType = when (currentRoute) {
      trainingListRoute, trainingDetailsRoute, createTrainingRoute -> NavigationType.TRAINING
      profileResumeRoute -> NavigationType.PROFILE
      else -> NavigationType.EXERCISE
    }
    val isShowFab = when (currentRoute) {
      trainingListRoute, exerciseListRoute -> true
      else -> false
    }

    TesteAppStateful(
      navController = navController,
      currentDestination = currentDestination,
      navType = navType,
      isShowFab = isShowFab
    ) {
      TesteAppNavHost(navController = navController)
    }
  }
}

@Composable
fun TesteAppStateful(
  navController: NavHostController,
  currentDestination: NavDestination?,
  isShowBottomBar: Boolean = true,
  isShowFab: Boolean = true,
  isShowTopBar: Boolean = true,
  navType: NavigationType = NavigationType.EXERCISE,
  content: @Composable () -> Unit
) {
  Scaffold(
    bottomBar = {
      if (isShowBottomBar) {
        BottomBarNavigation(
          currentDestination = currentDestination,
          navHostController = navController
        )
      }
    },
    floatingActionButton = {
      if (isShowFab) {
        ExtendedFloatingActionButton(
          icon = {
            Icon(
              imageVector = Icons.Rounded.Add,
              contentDescription = "Adicionar"
            )
          },
          text = {
            Text("Adicionar")
          },
          onClick = {
            when (navType) {
              NavigationType.EXERCISE -> {
                navController.navigateToCreateExercise()
              }

              NavigationType.TRAINING -> {
                navController.navigateToCreateTraining()
              }

              NavigationType.PROFILE -> {
                Log.d(TAG, "Profile")
              }
            }
          }
        )
      }
    }
  ) {
    Box(
      modifier = Modifier
        .padding(it)
    ) {
      content()
    }
  }
}

@Composable
fun LoadingScreen() {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize()
  ) {
    CircularProgressIndicator(modifier = Modifier.size(120.dp))
  }
}