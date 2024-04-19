package com.example.testeapp.presentation.screens.exercises

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.viewmodels.ExerciseDetailsUiState
import com.example.testeapp.presentation.viewmodels.ExerciseDetailsViewModel

@Composable
fun ExerciseDetailsScreen(
  viewModel: ExerciseDetailsViewModel = hiltViewModel(),
  navHostController: NavHostController,
  exerciseId: String
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  DisposableEffect(Unit) {
    viewModel.loadExerciseDetails(exerciseId)
    onDispose {
      viewModel.cancelExerciseDetailsRequest()
    }
  }

  when (uiState) {
    is ExerciseDetailsUiState.Loading -> {
      Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(
          Modifier.align(Alignment.Center)
        )
      }
    }

    is ExerciseDetailsUiState.Sucess -> {
      val exercise = (uiState as ExerciseDetailsUiState.Sucess).exercise
      Column {
        Text(text = exercise.id)
        Text(text = exercise.name)
      }
    }

    is ExerciseDetailsUiState.Error -> {
      navHostController.popBackStack()
    }
  }
}