package com.example.testeapp.presentation.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.viewmodels.CreateExerciseViewModel

@Composable
fun CreateExerciseScreen(
  viewModel: CreateExerciseViewModel = hiltViewModel(),
  navHostController: NavHostController,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Column {
    OutlinedTextField(
      value = uiState.name,
      label = { Text(text = "Nome") },
      onValueChange = uiState.onNameChange,
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
      value = uiState.observations,
      label = { Text(text = "Observações") },
      onValueChange = uiState.onObservationsChange,
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
      value = uiState.image,
      label = { Text(text = "Imagem") },
      onValueChange = uiState.onImageChange,
      modifier = Modifier.fillMaxWidth()
    )

    Button(onClick = {
      viewModel.createExercise()
      navHostController.popBackStack()
    }) {
      Text(text = "Adicionar")
    }
  }
}