package com.example.testeapp.presentation.screens.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.entities.TrainingPresentation
import com.example.testeapp.presentation.viewmodels.TrainingViewModel

@Composable
fun TrainingListScreen(
  viewModel: TrainingViewModel = hiltViewModel()
) {

  val trainingState = remember { mutableStateOf<TrainingPresentation?>(null) }

  LaunchedEffect(Unit) {
    viewModel.getTrainingById("DAfQDFlY5gVt28uSMrhV") {
      trainingState.value = it
    }
  }

  Column {
    Text(text = "Training List")
    trainingState.value?.let { TrainingItem(it) }
  }
}

@Composable
fun TrainingItem(training: TrainingPresentation) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Column {
      Text(text = training.name)
      Text(text = training.description)
      Text(text = training.date)
      for (e in training.exercises) {
        Text(text = e.name)
      }
    }
  }
}