package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.components.LoadingScreen
import com.example.testeapp.presentation.navigation.exercise.navigateToUpdateExercise
import com.example.testeapp.presentation.navigation.training.navigateToUpdateTraining
import com.example.testeapp.presentation.viewmodels.TrainingDetailsUiState
import com.example.testeapp.presentation.viewmodels.TrainingDetailsViewModel

@Composable
fun TrainingDetailsScreen(
  viewModel: TrainingDetailsViewModel = hiltViewModel(),
  navHostController: NavHostController,
  trainingId: String
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  DisposableEffect(Unit) {
    viewModel.loadTrainingDetails(trainingId)
    onDispose {
      viewModel.cancelTrainingDetailsRequest()
    }
  }

  when (uiState) {
    is TrainingDetailsUiState.Loading -> {
      LoadingScreen()
    }
    is TrainingDetailsUiState.Sucess -> {
      val training = (uiState as TrainingDetailsUiState.Sucess).training
      Column {
        Text(text = training.id)
        Button(onClick = {
          navHostController.navigateToUpdateTraining(id = training.id)
        }) {
          Text(text = "Editar")
        }
      }
    }
    is TrainingDetailsUiState.Error -> {
      navHostController.popBackStack()
    }
  }
}