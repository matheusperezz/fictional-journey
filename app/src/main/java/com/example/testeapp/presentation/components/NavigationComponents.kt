package com.example.testeapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.navigation.bottomAppBarItems

@Composable
fun BottomBarNavigation(
  currentDestination: NavDestination?,
  navHostController: NavHostController
) {
  NavigationBar {
    bottomAppBarItems.forEach { screen ->
      val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
      val icon = screen.icon
      NavigationBarItem(selected = isSelected, onClick = {
        navHostController.navigate(screen.route) {
          popUpTo(navHostController.graph.findStartDestination().id) {
            saveState = true
          }
          launchSingleTop = true
          restoreState = true
        }
      }, icon = {
        Icon(imageVector = icon, contentDescription = null)
      }, label = {
        Text(text = screen.label)
      })
    }
  }
}