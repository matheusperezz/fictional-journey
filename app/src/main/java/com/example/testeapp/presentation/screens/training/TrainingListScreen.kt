package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testeapp.domain.entities.TrainingPresentation
import com.example.testeapp.presentation.components.LoadingScreen
import com.example.testeapp.presentation.viewmodels.TrainingUiState
import com.example.testeapp.presentation.viewmodels.TrainingViewModel
import com.google.firebase.Timestamp

@Composable
fun TrainingListScreen(
  viewModel: TrainingViewModel = hiltViewModel()
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.getTrainings()
  }

  when (uiState) {
    is TrainingUiState.Loading -> {
      LoadingScreen()
    }

    is TrainingUiState.Success -> {
      val trainings = (uiState as TrainingUiState.Success).trainings
      TrainingList(trainings, onLongTrainingClick = { training ->
        viewModel.deleteTraining(training.id)
      })
    }

    is TrainingUiState.Error -> {
      Text(text = (uiState as TrainingUiState.Error).message)
    }
  }
}

@Composable
fun TrainingList(
  trainings: List<TrainingPresentation>,
  onTrainingClick: (TrainingPresentation) -> Unit = {},
  onLongTrainingClick: (TrainingPresentation) -> Unit = {}
) {
  LazyColumn(
    verticalArrangement = spacedBy(8.dp),
  ) {
    items(trainings) { training ->
      TrainingItem(training, onTrainingClick, onLongTrainingClick)
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrainingItem(
  training: TrainingPresentation,
  onTrainingClick: (TrainingPresentation) -> Unit = {},
  onLongTrainingClick: (TrainingPresentation) -> Unit = {}
) {
  Card(modifier = Modifier
    .fillMaxWidth()
    .combinedClickable(
      onClick = { onTrainingClick(training) },
      onLongClick = { onLongTrainingClick(training) }
    )) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Text(text = training.name, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
      Text(text = training.description)
      Text(text = training.date.toString())
      if (training.exercises.isNotEmpty()) {
        Text(text = "Exercicios:")
        training.exercises.forEach { exercise ->
          Text(text = exercise.name)
        }
      } else {
        Text(text = "Sem exercícios cadastrados")
      }
    }
  }
}