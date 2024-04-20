package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.presentation.screens.exercises.ExerciseItem
import com.example.testeapp.presentation.viewmodels.CreateTrainingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTrainingSreen(
  viewModel: CreateTrainingViewModel = hiltViewModel(),
  navHostController: NavHostController,
  trainingId: String = ""
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val isUpdating = trainingId != ""
  var showBottomSheet by remember { mutableStateOf(false) }
  val sheetState = rememberModalBottomSheetState()

  DisposableEffect(trainingId) {
    if (isUpdating) {
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

    Row {
      Text(text = "Exercícios")
      IconButton(onClick = { showBottomSheet = true }) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
      }
    }

    if (uiState.trainingExercises.isNotEmpty()) {
      LazyColumn {
        items(uiState.trainingExercises) { exercise ->
          ExerciseItemOnTraining(exercise = exercise, onIconClick = {
            viewModel.unrollExerciseOnTraining(trainingId, it.id)
          })
        }
      }
    } else {
      Text(text = "Nenhum exercício adicionado")
    }
    Button(onClick = {
      if (isUpdating) {
        viewModel.updateTraining(trainingId)
        navHostController.popBackStack()
      } else {
        viewModel.createTraining()
        navHostController.popBackStack()
      }
    }) {
      Text(text = "Salvar")
    }
  }

  if (showBottomSheet) {
    ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState) {
      LazyColumn {
        items(uiState.exercises) { exercise ->
          ExerciseItem(exercise = exercise, size = 80.dp, onExerciseClick = {
            viewModel.enrollExerciseOnTraining(trainingId, it.id)
            showBottomSheet = false
          })
        }
      }
    }
  }
}

@Composable
fun ExerciseItemOnTraining(exercise: Exercise, onIconClick: (Exercise) -> Unit = {}) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(4.dp)
      .clickable { onIconClick(exercise) }
  ) {
    Column {
      Text(text = exercise.name)
      Text(text = exercise.observations)
    }

    Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
  }
}