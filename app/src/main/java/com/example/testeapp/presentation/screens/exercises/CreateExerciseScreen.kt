package com.example.testeapp.presentation.screens.exercises

import android.util.Log
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
import com.example.testeapp.presentation.viewmodels.CreateExerciseViewModel
import com.example.testeapp.utils.TAG

@Composable
fun CreateExerciseScreen(
  viewModel: CreateExerciseViewModel = hiltViewModel(),
  navHostController: NavHostController,
  exerciseId: String = ""
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val isUpdating = exerciseId != ""

  DisposableEffect(exerciseId) {
    Log.d(TAG, "CreateExerciseScreen: CurrentId: $exerciseId")
    if (isUpdating) {
      viewModel.getExerciseById(exerciseId)
    }
    onDispose {}
  }

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
      if (isUpdating){
        viewModel.updateExercise(exerciseId)
        navHostController.popBackStack()
      } else {
        viewModel.createExercise()
        navHostController.popBackStack()
      }
    }) {
      Text(text = "Salvar")
    }
  }
}