package com.example.testeapp.presentation.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testeapp.R
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.presentation.components.LoadingScreen
import com.example.testeapp.presentation.navigation.exercise.navigateToUpdateExercise
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
      LoadingScreen()
    }

    is ExerciseDetailsUiState.Sucess -> {
      val exercise = (uiState as ExerciseDetailsUiState.Sucess).exercise
      ExerciseDetails(exercise, onEditClick = {
        navHostController.navigateToUpdateExercise(id = exerciseId)
      })
    }

    is ExerciseDetailsUiState.Error -> {
      navHostController.popBackStack()
    }
  }
}

@Composable
fun ExerciseDetails(exercise: Exercise, onEditClick: () -> Unit = {}) {
  Column(
    modifier = Modifier.verticalScroll(rememberScrollState())
  ) {
    AsyncImage(
      model = exercise.image,
      contentDescription = null,
      placeholder = painterResource(id = R.drawable.ic_fitness_center),
      error = painterResource(id = R.drawable.ic_fitness_center),
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxWidth(),
    )
    Row {
      Text(text = exercise.name)
      IconButton(onClick = { onEditClick() }) {
        Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
      }
    }

    Text(text = exercise.observations)
  }
}