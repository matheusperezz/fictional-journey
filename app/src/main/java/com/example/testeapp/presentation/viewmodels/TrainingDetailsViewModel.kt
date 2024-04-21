package com.example.testeapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.TrainingPresentation
import com.example.testeapp.domain.usecases.TrainingUseCase
import com.example.testeapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TrainingDetailsUiState {
  object Loading: TrainingDetailsUiState()
  data class Sucess(val training: TrainingPresentation) : TrainingDetailsUiState()
  data class Error(val message: String): TrainingDetailsUiState()
}

@HiltViewModel
class TrainingDetailsViewModel @Inject constructor(
  private val useCase: TrainingUseCase
): ViewModel() {
  private val _uiState = MutableStateFlow<TrainingDetailsUiState>(TrainingDetailsUiState.Loading)
  val uiState = _uiState.asStateFlow()

  fun loadTrainingDetails(trainingId: String){
    viewModelScope.launch {
      _uiState.value = TrainingDetailsUiState.Loading
      try {
        val fetchedTraining = useCase.getTrainingById(trainingId)
        if (fetchedTraining.id != ""){
          _uiState.value = TrainingDetailsUiState.Sucess(fetchedTraining)
        } else {
          _uiState.value = TrainingDetailsUiState.Error("Treino não encontrado.")
        }
      } catch (e: Exception){
        _uiState.value = TrainingDetailsUiState.Error(e.message.toString())
      }
    }
  }

  fun cancelTrainingDetailsRequest(){
     Log.d(TAG, "cancelTrainingDetailsRequest: cancelling the request")
  }
}