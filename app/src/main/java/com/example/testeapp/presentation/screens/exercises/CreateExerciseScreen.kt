package com.example.testeapp.presentation.screens.exercises

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testeapp.R
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

  Column(
    modifier = Modifier.verticalScroll(rememberScrollState())
  ) {

    AsyncImage(
      model = uiState.image,
      contentDescription = "Image not found",
      placeholder = painterResource(id = R.drawable.imagenotfound),
      error = painterResource(id = R.drawable.imagenotfound),
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
    )

    Box(modifier = Modifier.padding(8.dp)) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
          if (isUpdating) {
            viewModel.updateExercise(exerciseId)
            navHostController.popBackStack()
          } else {
            viewModel.createExercise()
            navHostController.popBackStack()
          }
        }, modifier = Modifier.fillMaxWidth()) {
          Text(text = "Salvar")
        }
      }
    }
  }
}