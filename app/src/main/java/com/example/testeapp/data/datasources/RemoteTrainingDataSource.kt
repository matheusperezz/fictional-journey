package com.example.testeapp.data.datasources

import com.example.testeapp.domain.entities.Training
import kotlinx.coroutines.flow.Flow

interface RemoteTrainingDataSource {
  suspend fun addTraining(training: Training): String
  suspend fun getTrainings(): Flow<List<Training>>
  suspend fun getTrainingById(id: String): Training
  suspend fun deleteTraining(id: String): String
  suspend fun updateTraining(training: Training): String
}