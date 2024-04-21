package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import com.example.testeapp.utils.dateMapper
import java.util.Calendar
import java.util.Date

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
  var showDatePicker by remember { mutableStateOf(false) }
  val datePickerState = rememberDatePickerState()

  val sheetState = rememberModalBottomSheetState()

  DisposableEffect(trainingId) {
    if (isUpdating) {
      viewModel.getTrainingById(trainingId)
    }
    onDispose {}
  }

  Column(
    modifier = Modifier.padding(8.dp)
  ) {
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

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
          val calendar = Calendar.getInstance().apply {
            timeInMillis = uiState.date.time
            add(Calendar.DAY_OF_MONTH, -1)
          }
          val time = calendar.timeInMillis
          datePickerState.setSelection(time)
          showDatePicker = true
        }
    ) {
      Text(text = dateMapper(uiState.date))
      Icon(imageVector = Icons.Rounded.DateRange, contentDescription = null)
    }

    if (showDatePicker) {
      DatePickerDialog(
        onDismissRequest = {
          showDatePicker = false
        }, confirmButton = {
          Button(onClick = {
            val calendar = Calendar.getInstance().apply {
              timeInMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
              add(Calendar.DAY_OF_MONTH, 1)
            }
            val date = Date(calendar.timeInMillis)
            uiState.date = date
            showDatePicker = false
          }) {
            Text(text = "Escolher data")
          }
        }) {
        DatePicker(state = datePickerState)
      }
    }

    Row(
      modifier = Modifier
        .padding(8.dp)
        .clickable { showBottomSheet = true }
    ) {
      Icon(
        imageVector = Icons.Rounded.Add,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary
      )
      Text(text = "Adicionar exercícios", color = MaterialTheme.colorScheme.primary)
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
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { onIconClick(exercise) }
  ) {
    Text(text = exercise.name)
    Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
  }
}