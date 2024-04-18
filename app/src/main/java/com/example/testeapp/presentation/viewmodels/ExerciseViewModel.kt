package com.example.testeapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.usecases.ExerciseUseCase
import com.example.testeapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ExerciseUiState {
  object Loading : ExerciseUiState()
  data class Success(val exercises: List<Exercise>) : ExerciseUiState()
  data class Error(val message: String) : ExerciseUiState()
}

@HiltViewModel
class ExerciseViewModel @Inject constructor(
  private val useCase: ExerciseUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)
  val uiState = _uiState

  fun getExercises() = viewModelScope.launch {
    _uiState.value = ExerciseUiState.Loading
    try {
      useCase.getExercises().collect { exercises ->
        _uiState.value = ExerciseUiState.Success(exercises)
      }
    } catch (e: Exception) {
      _uiState.value = ExerciseUiState.Error(e.message.toString())
    }
  }

  fun addExercise(exercise: Exercise) = viewModelScope.launch {
    _uiState.value = ExerciseUiState.Loading
    try {
      useCase.addExercise(exercise)
      _uiState.value = ExerciseUiState.Success(useCase.getExercises().first())
    } catch (e: Exception) {
      _uiState.value = ExerciseUiState.Error(e.message.toString())
    }
  }

  fun getExerciseById(id: String, callback: (Exercise) -> Unit) {
    viewModelScope.launch {
      try {
        val exercise = useCase.getExerciseById(id)
        callback(exercise)
      } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
      }
    }
  }

  fun updateExercise(exercise: Exercise) {
    _uiState.value = ExerciseUiState.Loading
    viewModelScope.launch {
      try {
        useCase.updateExercise(exercise)
        _uiState.value = ExerciseUiState.Success(useCase.getExercises().first())
      } catch (e: Exception) {
        _uiState.value = ExerciseUiState.Error(e.message.toString())
      }
    }
  }

  fun deleteExercise(id: String) {
    _uiState.value = ExerciseUiState.Loading
    viewModelScope.launch {
      try {
        useCase.deleteExercise(id)
        _uiState.value = ExerciseUiState.Success(useCase.getExercises().first())
      } catch (e: Exception) {
        _uiState.value = ExerciseUiState.Error(e.message.toString())
      }
    }
  }

}