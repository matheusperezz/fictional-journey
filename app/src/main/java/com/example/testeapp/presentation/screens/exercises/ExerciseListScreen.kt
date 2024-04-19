package com.example.testeapp.presentation.screens.exercises

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.testeapp.R
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.presentation.viewmodels.ExerciseUiState
import com.example.testeapp.presentation.viewmodels.ExerciseViewModel

@Composable
fun ExerciseListScreen(
  viewModel: ExerciseViewModel = hiltViewModel(),
  onExerciseClick: (Exercise) -> Unit = {}
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var showDialog by remember { mutableStateOf(false) }
  var currentLongPressedExercise by remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    viewModel.getExercises()
  }

  when (uiState) {
    is ExerciseUiState.Loading -> {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
      ) {
        CircularProgressIndicator(modifier = Modifier.size(120.dp))
      }
    }

    is ExerciseUiState.Error -> {
      val error = (uiState as ExerciseUiState.Error).message
      Column {
        Text(text = error)
      }
    }

    is ExerciseUiState.Success -> {
      val exercises = (uiState as ExerciseUiState.Success).exercises
      ExerciseList(exercises = exercises, onExerciseClick = onExerciseClick, onLongClick = {
        currentLongPressedExercise = it.id
        showDialog = true
      })
      if (showDialog){
        AlertDialog(
          onDismissRequest = { showDialog = false },
          confirmButton = {
            TextButton(onClick = {
              viewModel.deleteExercise(currentLongPressedExercise)
              showDialog = false
            }) {
              Text(text = "Excluir")
            }
          },
          dismissButton = {
            TextButton(onClick = {
              showDialog = false
            }) {
              Text(text = "Cancelar")
            }
          },
          title = {
            Text(text = "Excluir exercício")
          },
          text = {
            Text(text = "Deseja excluir o exercício?")
          },
        )
      }
    }
  }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, onExerciseClick: (Exercise) -> Unit = {}, onLongClick: (Exercise) -> Unit = {}) {
  LazyColumn {
    items(exercises) { exercise ->
      ExerciseItem(exercise = exercise, onExerciseClick = onExerciseClick, onLongClick = onLongClick)
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseItem(
  exercise: Exercise,
  onExerciseClick: (Exercise) -> Unit = {},
  onLongClick: (Exercise) -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .combinedClickable(
        onClick = { onExerciseClick(exercise) },
        onLongClick = { onLongClick(exercise) }
      )

  ) {
    Row {
      AsyncImage(
        model = exercise.image,
        contentDescription = null,
        placeholder = painterResource(id = R.drawable.ic_fitness_center),
        error = painterResource(id = R.drawable.ic_fitness_center),
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(120.dp),
      )

      Column(
        modifier = Modifier.padding(4.dp)
      ) {
        Text(text = exercise.name)
        Text(text = exercise.observations)
        Text(text = exercise.id)
      }
    }
  }
}