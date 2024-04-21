package com.example.testeapp.presentation.screens.exercises

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.testeapp.R
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.presentation.components.LoadingScreen
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
      LoadingScreen()
    }

    is ExerciseUiState.Error -> {
      val error = (uiState as ExerciseUiState.Error).message
      Column {
        Text(text = error)
      }
    }

    is ExerciseUiState.Success -> {
      val exercises = (uiState as ExerciseUiState.Success).exercises
      if (exercises.isNotEmpty()) {
        ExerciseList(exercises = exercises, onExerciseClick = onExerciseClick, onLongClick = {
          currentLongPressedExercise = it.id
          showDialog = true
        })
      } else {
        Text(
          text = """
            Nenhum exercício cadastrado!
                        
            Para adicionar, clique no botão no canto inferior direito.
                        
            Para Excluir, segure o clique em cima do exercício :)
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
fun ExerciseList(
  exercises: List<Exercise>,
  onExerciseClick: (Exercise) -> Unit = {},
  onLongClick: (Exercise) -> Unit = {}
) {
  LazyColumn(
    verticalArrangement = spacedBy(8.dp),
    modifier = Modifier
      .padding(horizontal = 8.dp)
      .padding(top = 8.dp)
  ) {
    items(exercises) { exercise ->
      ExerciseItem(
        exercise = exercise,
        onExerciseClick = onExerciseClick,
        onLongClick = onLongClick
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseItem(
  exercise: Exercise,
  size: Dp = 120.dp,
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
        modifier = Modifier.size(size),
      )

      Column(
        modifier = Modifier.padding(6.dp)
      ) {
        Text(
          text = exercise.name,
          style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = exercise.observations,
          maxLines = 3,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}