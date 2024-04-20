package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.viewmodels.CreateTrainingViewModel

@Composable
fun CreateTrainingSreen(
  viewModel: CreateTrainingViewModel = hiltViewModel(),
  navHostController: NavHostController,
  trainingId: String = ""
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val isUpdating = trainingId != ""

  DisposableEffect(trainingId) {
    if (isUpdating){
      viewModel.getTrainingById(trainingId)
    }
    onDispose {}
  }

  Column {
    OutlinedTextField(
      value = uiState.name,
      onValueChange = uiState.onNameChange,
      label = { Text(text = "Nome") },
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
      value = uiState.description,
      onValueChange = uiState.onDescriptionChange,
      label = { Text(text = "Descrição") },
      modifier = Modifier.fillMaxWidth()
    )

    Button(onClick = {
      if (isUpdating) {

      } else {
        viewModel.createTraining()
        navHostController.popBackStack()
      }
    }) {
      Text(text = "Salvar")
    }
  }
}