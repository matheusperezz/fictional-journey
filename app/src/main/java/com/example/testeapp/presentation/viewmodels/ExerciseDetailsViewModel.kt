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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ExerciseDetailsUiState {
  object Loading: ExerciseDetailsUiState()
  data class Sucess(val exercise: Exercise) : ExerciseDetailsUiState()
  data class Error(val message: String): ExerciseDetailsUiState()
}

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
  private val useCase: ExerciseUseCase
): ViewModel() {
  private val _uiState = MutableStateFlow<ExerciseDetailsUiState>(ExerciseDetailsUiState.Loading)
  val uiState = _uiState.asStateFlow()

  fun loadExerciseDetails(exerciseId: String){
    viewModelScope.launch {
      _uiState.value = ExerciseDetailsUiState.Loading
      try {
        val fetchedExercise = useCase.getExerciseById(exerciseId)
        if (fetchedExercise.id != ""){
          _uiState.update { ExerciseDetailsUiState.Sucess(fetchedExercise) }
        } else {
          _uiState.value = ExerciseDetailsUiState.Error("Exercício não encontrado.")
        }
      } catch (e: Exception){
        _uiState.value = ExerciseDetailsUiState.Error(e.message.toString())
      }
    }
  }

  fun cancelExerciseDetailsRequest(){
    Log.d(TAG, "cancelExerciseDetailsRequest: cancelling the request")
  }

}