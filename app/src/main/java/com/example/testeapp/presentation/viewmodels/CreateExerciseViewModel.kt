package com.example.testeapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.usecases.ExerciseUseCase
import com.example.testeapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateExerciseUiState(
  var name: String = "",
  var observations: String = "",
  var image: String = "",
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

  fun getExerciseById(id: String) {
    viewModelScope.launch {
      try {
        val exercise = useCase.getExerciseById(id)
        _uiState.value = _uiState.value.copy(
          name = exercise.name,
          observations = exercise.observations,
          image = exercise.image
        )
      } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
      }
    }
  }

  fun updateExercise(exerciseId: String) {
    val exercise = Exercise(
      id = exerciseId,
      name = _uiState.value.name,
      image = _uiState.value.image,
      observations = _uiState.value.observations
    )
    viewModelScope.launch {
      useCase.updateExercise(exercise)
    }
  }

}