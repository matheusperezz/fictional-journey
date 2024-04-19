package com.example.testeapp.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.testeapp.presentation.navigation.exercise.exerciseGraph
import com.example.testeapp.presentation.navigation.exercise.exerciseGraphRoute
import com.example.testeapp.presentation.navigation.exercise.navigateToExerciseGraph
import com.example.testeapp.presentation.navigation.profile.navigateToProfileGraph
import com.example.testeapp.presentation.navigation.profile.profileGraph
import com.example.testeapp.presentation.navigation.profile.profileGraphRoute
import com.example.testeapp.presentation.navigation.training.navigateToTrainingGraph
import com.example.testeapp.presentation.navigation.training.trainingGraph
import com.example.testeapp.presentation.navigation.training.trainingGraphRoute
import kotlin.reflect.KFunction

internal const val mainGraphRoute = "mainGraph"

fun NavGraphBuilder.mainGraph(navHostController: NavHostController) {
    navigation(
        startDestination = exerciseGraphRoute,
        route = mainGraphRoute
    ) {
        exerciseGraph(navHostController)
        trainingGraph(navHostController)
        profileGraph(navHostController)
    }
}

fun NavHostController.navigateToMainGraph() {
    navigate(mainGraphRoute)
}

fun NavController.navigateSingleTopWithPopUpTo(
    item: BottomAppBarItem
){
    val (route, navigate) = when (item){
        BottomAppBarItem.Training -> Pair(
            trainingGraphRoute,
            ::navigateToTrainingGraph
        )
        BottomAppBarItem.Exercise -> Pair(
            exerciseGraphRoute,
            ::navigateToExerciseGraph
        )
        BottomAppBarItem.Profile -> Pair(
            profileGraphRoute,
            ::navigateToProfileGraph
        )
    }

    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route)
    }

    navigate(navOptions)
}