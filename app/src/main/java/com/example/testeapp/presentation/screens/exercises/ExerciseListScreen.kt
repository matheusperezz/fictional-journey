package com.example.testeapp.presentation.screens.exercises

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
  onExerciseClick: () -> Unit = {}
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
      ExerciseList(exercises = exercises, onExerciseClick = onExerciseClick)
    }
  }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, onExerciseClick: () -> Unit = {}) {
  LazyColumn {
    items(exercises) { exercise ->
      ExerciseItem(exercise = exercise, onExerciseClick = onExerciseClick)
    }
  }
}

@Composable
fun ExerciseItem(exercise: Exercise, onExerciseClick: () -> Unit = {}) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onExerciseClick() }
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