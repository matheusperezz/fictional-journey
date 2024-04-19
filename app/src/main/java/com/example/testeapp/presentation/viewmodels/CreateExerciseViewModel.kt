package com.example.testeapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.usecases.ExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateExerciseUiState(
  val name: String = "",
  val observations: String = "",
  val image: String = "",
  val onNameChange: (String) -> Unit = {},
  val onObservationsChange: (String) -> Unit = {},
  val onImageChange: (String) -> Unit = {},
)

@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
  private val useCase: ExerciseUseCase,
) : ViewModel() {

  private val _uiState: MutableStateFlow<CreateExerciseUiState> = MutableStateFlow(
    CreateExerciseUiState()
  )
  val uiState = _uiState.asStateFlow()

  init {
    _uiState.value = CreateExerciseUiState(
      onNameChange = { name ->
        _uiState.value = _uiState.value.copy(name = name)
      },
      onObservationsChange = { observations ->
        _uiState.value = _uiState.value.copy(observations = observations)
      },
      onImageChange = { image ->
        _uiState.value = _uiState.value.copy(image = image)
      }
    )
  }

  fun createExercise(){
    val exercise = Exercise(
      name = _uiState.value.name,
      image = _uiState.value.image,
      observations = _uiState.value.observations
    )
    viewModelScope.launch {
      useCase.addExercise(exercise)
    }
  }

}