package com.example.testeapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.usecases.ExerciseUseCase
import com.example.testeapp.domain.usecases.TrainingUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class CreateTrainingUiState(
  var name: String = "",
  var description: String = "",
  var date: Date = Date(),

  val onNameChange: (String) -> Unit = {},
  val onDescriptionChange: (String) -> Unit = {},

  val exercises: List<Exercise> = emptyList(),
  val trainingExercises: MutableList<Exercise> = mutableListOf(),
  val addExercise: (Exercise) -> Unit = { exercise ->
    trainingExercises.add(exercise)
  },
  val removeExercise: (Exercise) -> Unit = { exercise ->
    trainingExercises.remove(exercise)
  }
)

@HiltViewModel
class CreateTrainingViewModel @Inject constructor(
  private val trainingUseCase: TrainingUseCase,
  private val exerciseUseCase: ExerciseUseCase
) : ViewModel() {

  private val _uiState: MutableStateFlow<CreateTrainingUiState> = MutableStateFlow(
    CreateTrainingUiState()
  )
  val uiState = _uiState.asStateFlow()
  private val _exercises: MutableStateFlow<List<Exercise>> = MutableStateFlow(emptyList())
  val exercises = _exercises.asStateFlow()

  init {
    _uiState.value = CreateTrainingUiState(
      onNameChange = { name ->
        _uiState.value = _uiState.value.copy(name = name)
      },
      onDescriptionChange = { description ->
        _uiState.value = _uiState.value.copy(description = description)
      },
      addExercise = { exercise ->
        _uiState.value =
          _uiState.value.copy(trainingExercises = _uiState.value.trainingExercises.apply {
            add(exercise)
          })
      },
      removeExercise = { exercise ->
        _uiState.value =
          _uiState.value.copy(trainingExercises = _uiState.value.trainingExercises.apply {
            remove(exercise)
          })
      }
    )
    loadExercises()
    getExercises()
  }

  private fun loadExercises() {
    viewModelScope.launch {
      _exercises.value = exerciseUseCase.getExercises().first()
    }
  }

  fun getTrainingById(id: String) {
    viewModelScope.launch {
      val training = trainingUseCase.getTrainingById(id)
      _uiState.value = _uiState.value.copy(
        name = training.name,
        description = training.description,
        date = training.date,
        trainingExercises = training.exercises.toMutableList()
      )
    }
  }

  fun createTraining() {
    viewModelScope.launch {
      val training = Training(
        name = _uiState.value.name,
        description = _uiState.value.description,
        date = Timestamp(_uiState.value.date),
        exercises = _uiState.value.trainingExercises.map { it.id }
      )
      trainingUseCase.addTraining(training)
    }
  }

  suspend fun createEmptyTraining(): String {
    val training = Training()
    return trainingUseCase.addTraining(training)
  }

  private fun getExercises() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(
        exercises = exerciseUseCase.getExercises().first()
      )
    }
  }

  fun updateTraining(trainingId: String) {
    val training = Training(
      id = trainingId,
      name = _uiState.value.name,
      description = _uiState.value.description,
      date = Timestamp(_uiState.value.date)
    )
    viewModelScope.launch {
      trainingUseCase.updateTraining(training)
    }
  }

  fun enrollExerciseOnTraining(trainingId: String, exerciseId: String) {
    viewModelScope.launch {
      trainingUseCase.enrollExerciseInTraining(trainingId, exerciseId)
      val training = trainingUseCase.getTrainingById(trainingId)
      _uiState.value = _uiState.value.copy(
        trainingExercises = training.exercises.toMutableList()
      )
    }
  }

  fun unrollExerciseOnTraining(trainingId: String, exerciseId: String) {
    viewModelScope.launch {
      trainingUseCase.unrollExerciseInTraining(trainingId, exerciseId)
      val training = trainingUseCase.getTrainingById(trainingId)
      _uiState.value = _uiState.value.copy(
        trainingExercises = training.exercises.toMutableList()
      )
    }
  }

  fun createTrainingAndEnrollExercises(callback: (String?) -> Unit) {
    viewModelScope.launch {
      val training = Training(
        name = uiState.value.name,
        description = uiState.value.description,
        date = Timestamp(uiState.value.date),
        exercises = uiState.value.trainingExercises.map { it.id }
      )
      val id = trainingUseCase.addTraining(training)
      uiState.value.trainingExercises.forEach { exercise ->
        trainingUseCase.enrollExerciseInTraining(id, exercise.id)
      }
      callback(id)
    }
  }
}

