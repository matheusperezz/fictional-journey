package com.example.testeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.presentation.theme.TesteAppTheme
import com.example.testeapp.presentation.viewmodels.ExerciseUiState
import com.example.testeapp.presentation.viewmodels.ExerciseViewModel
import com.example.testeapp.utils.TAG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TesteAppTheme {
        val viewmodel: ExerciseViewModel by viewModels()
        val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
        viewmodel.getExercises()
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          when (uiState) {
            is ExerciseUiState.Loading -> {
              CircularProgressIndicator()
            }

            is ExerciseUiState.Error -> {
              val e = (uiState as ExerciseUiState.Error).message
              Greeting(name = e)
            }

            is ExerciseUiState.Success -> {
              val exercises = (uiState as ExerciseUiState.Success).exercises
              if (exercises.isEmpty()) {
                Greeting(name = "lista vazia")
              } else {
                Column {
                  for (e in exercises) {
                    Greeting(name = e.id)
                  }
                  Button(onClick = {
                    viewmodel.addExercise(Exercise("", "teste", "teste", "teste"))
                  }) {
                    Text(text = "Adicionar")
                  }

                  Button(onClick = {
                    viewmodel.getExerciseById("mQUlRX92BlR7q5dX0Vcu") {
                      Log.d(TAG, it.toString())
                    }
                  }) {
                    Text(text = "Pegar o id")
                  }

                  Button(onClick = {
                    viewmodel.getExerciseById("mQUlRX92BlR7q5dX0Vcu") {
                      viewmodel.updateExercise(it.copy(name = "teste2"))
                    }
                  }) {
                    Text(text = "Atualizar")
                  }

                  Button(onClick = {
                    viewmodel.deleteExercise("mQUlRX92BlR7q5dX0Vcu")
                  }) {
                    Text(text = "Deletar")
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  TesteAppTheme {
    Greeting("Android")
  }
}