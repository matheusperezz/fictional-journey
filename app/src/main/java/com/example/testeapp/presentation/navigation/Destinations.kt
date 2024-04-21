package com.example.testeapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.testeapp.presentation.navigation.exercise.exerciseGraphRoute
import com.example.testeapp.presentation.navigation.profile.profileGraphRoute
import com.example.testeapp.presentation.navigation.training.trainingGraphRoute

sealed class BottomAppBarItem(
  val route: String,
  val icon: ImageVector,
  val label: String
) {
  object Exercise : BottomAppBarItem(exerciseGraphRoute, Icons.Rounded.Home, "Exercícios")
  object Training : BottomAppBarItem(trainingGraphRoute, Icons.Rounded.Build, "Treinos")
}

val bottomAppBarItems = listOf(
  BottomAppBarItem.Exercise,
  BottomAppBarItem.Training,
)