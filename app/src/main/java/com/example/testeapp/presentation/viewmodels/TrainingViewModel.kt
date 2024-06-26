package com.example.testeapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.TrainingPresentation
import com.example.testeapp.domain.usecases.TrainingUseCase
import com.example.testeapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TrainingUiState {
  object Loading : TrainingUiState()
  data class Success(val trainings: List<TrainingPresentation>) :
    TrainingUiState()

  data class Error(val message: String) : TrainingUiState()
}

@HiltViewModel
class TrainingViewModel @Inject constructor(
  private val useCase: TrainingUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow<TrainingUiState>(TrainingUiState.Loading)
  val uiState = _uiState

  fun getTrainings() = viewModelScope.launch {
    _uiState.value = TrainingUiState.Loading
    try {
      useCase.getTrainings().collect { trainings ->
        _uiState.value = TrainingUiState.Success(trainings)
      }
    } catch (e: Exception) {
      _uiState.value = TrainingUiState.Error(e.message.toString())
    }
  }

  init {
    getTrainings()
  }

  fun getTrainingById(id: String, callback: (TrainingPresentation) -> Unit) {
    viewModelScope.launch {
      try {
        val training = useCase.getTrainingById(id)
        callback(training)
      } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
      }
    }
  }

  fun deleteTraining(id: String) {
    _uiState.value = TrainingUiState.Loading
    viewModelScope.launch {
      try {
        useCase.deleteTraining(id)
        _uiState.value = TrainingUiState.Success(useCase.getTrainings().first())
      } catch (e: Exception) {
        _uiState.value = TrainingUiState.Error(e.message.toString())
      }
    }
  }
}