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
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.presentation.theme.TesteAppTheme
import com.example.testeapp.presentation.viewmodels.TrainingUiState
import com.example.testeapp.presentation.viewmodels.TrainingViewModel
import com.example.testeapp.utils.TAG
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint


// TODO: Separar as viewmodels durante o formulário de criação de treino
// TODO: Criar um formulário de criação de treino com lista de exercícios
// TODO: Não será necessário BottomNav
// TODO: Criar um formulário de criação de exercício como bottomFragment
// TODO: Talvez tela de login?
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TesteAppTheme {
        val viewmodel: TrainingViewModel by viewModels()
        val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
        viewmodel.getTrainings()
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          when (uiState) {
            is TrainingUiState.Loading -> {
              CircularProgressIndicator()
            }

            is TrainingUiState.Error -> {
              val e = (uiState as TrainingUiState.Error).message
              Greeting(name = e)
            }

            is TrainingUiState.Success -> {
              val trainings = (uiState as TrainingUiState.Success).trainings
              if (trainings.isEmpty()) {
                Greeting(name = "lista vazia")
              } else {
                Column {
                  for (e in trainings) {
                    Greeting(name = e.id)
                  }
                  Button(onClick = {
                    viewmodel.addTraining(Training("", "teste", "teste", Timestamp.now(), listOf(
                      "asdf", "idligado"
                    )))
                  }) {
                    Text(text = "Adicionar")
                  }

                  Button(onClick = {

                  }) {
                    Text(text = "Pegar o id")
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