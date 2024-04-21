package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testeapp.R
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.entities.TrainingPresentation
import com.example.testeapp.presentation.components.LoadingScreen
import com.example.testeapp.presentation.navigation.exercise.navigateToUpdateExercise
import com.example.testeapp.presentation.navigation.training.navigateToUpdateTraining
import com.example.testeapp.presentation.screens.exercises.ExerciseItem
import com.example.testeapp.presentation.viewmodels.TrainingDetailsUiState
import com.example.testeapp.presentation.viewmodels.TrainingDetailsViewModel
import com.example.testeapp.utils.dateMapper

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
      TrainingDetails(training = training) {
        navHostController.navigateToUpdateTraining(id = trainingId)
      }
    }

    is TrainingDetailsUiState.Error -> {
      navHostController.popBackStack()
    }
  }
}

@Composable
fun TrainingDetails(training: TrainingPresentation, onUpdateClick: () -> Unit) {
  Column(
    verticalArrangement = spacedBy(8.dp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {

    Text(
      text = training.name,
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center
    )
    Text(text = training.description)
    Text(text = "\uD83D\uDCC5 Data do treino: ${dateMapper(training.date)}")

    Column(modifier = Modifier.fillMaxWidth()) {
      if (training.exercises.isNotEmpty()) {

        Text(
          text = "Lista de Exercícios",
          fontSize = 18.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )

        training.exercises.forEach { exercise ->
          TrainingExerciseItem(exercise = exercise, modifier = Modifier.padding(8.dp))
        }
      } else {
        Text(text = "Nenhum exercício adicionado neste treino!", modifier = Modifier.fillMaxWidth())
      }
    }

    TextButton(onClick = { onUpdateClick() }, modifier = Modifier.fillMaxWidth()) {
      Text(text = "Editar este treino")
    }

  }
}

@Composable
fun TrainingExerciseItem(
  exercise: Exercise,
  modifier: Modifier = Modifier,
  onExerciseClick: (Exercise) -> Unit = {}
) {
  Row(
    horizontalArrangement = spacedBy(8.dp),
    modifier = modifier
      .fillMaxWidth()
      .clickable { onExerciseClick(exercise) }
  ) {
    AsyncImage(
      model = exercise.image,
      contentDescription = null,
      placeholder = painterResource(id = R.drawable.imagenotfound),
      error = painterResource(id = R.drawable.imagenotfound),
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(100.dp)
        .clip(RoundedCornerShape(4.dp)),
    )

    Column {
      Text(text = exercise.name)
      Text(text = exercise.observations)
    }
  }
}