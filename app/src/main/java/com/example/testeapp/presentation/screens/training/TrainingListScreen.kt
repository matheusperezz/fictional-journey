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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testeapp.domain.entities.TrainingPresentation
import com.example.testeapp.presentation.components.LoadingScreen
import com.example.testeapp.presentation.viewmodels.TrainingUiState
import com.example.testeapp.presentation.viewmodels.TrainingViewModel
import com.example.testeapp.utils.dateMapper
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TrainingListScreen(
  viewModel: TrainingViewModel = hiltViewModel(),
  onTrainingClick: (TrainingPresentation) -> Unit = {}
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var showDialog by remember { mutableStateOf(false) }
  var currentLongPressedTraining by remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    viewModel.getTrainings()
  }

  when (uiState) {
    is TrainingUiState.Loading -> {
      LoadingScreen()
    }

    is TrainingUiState.Success -> {
      val trainings = (uiState as TrainingUiState.Success).trainings
      if (trainings.isNotEmpty()){
        TrainingList(trainings, onTrainingClick = onTrainingClick, onLongTrainingClick = { training ->
          currentLongPressedTraining = training.id
          showDialog = true
        })
      } else {
        Text(
          text = """
            Nenhum treino cadastrado!
            
            Para adicionar, clique no botão no canto inferior direito.
            
            Para Excluir, segure o clique em cima do treino :)
          """.trimIndent(),
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
        )
      }

      if (showDialog) {
        AlertDialog(
          onDismissRequest = { showDialog = false },
          confirmButton = {
            TextButton(onClick = {
              viewModel.deleteTraining(currentLongPressedTraining)
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
            Text(text = "Excluir treino")
          },
          text = {
            Text(text = "Deseja excluir o treino?")
          },
        )
      }
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
    modifier = Modifier.padding(8.dp)
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
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      verticalArrangement = spacedBy(4.dp)
    ) {

      Text(
        text = training.name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
      )

      Text(
        text = training.description,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
      )

      Text(
        text = dateMapper(training.date),
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.End,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}