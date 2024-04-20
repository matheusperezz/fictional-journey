package com.example.testeapp.data.datasources

import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.entities.TrainingPresentation
import kotlinx.coroutines.flow.Flow

interface RemoteTrainingDataSource {
  suspend fun addTraining(training: Training): String
  suspend fun getTrainings(): Flow<List<TrainingPresentation>>
  suspend fun getTrainingById(id: String): TrainingPresentation
  suspend fun deleteTraining(id: String): String
  suspend fun updateTraining(training: Training): String
  suspend fun getExercisesFromTrainig(trainingId: String): Flow<List<Exercise>>
  suspend fun enrollExerciseInTraining(trainingId: String, exerciseId: String)
  suspend fun unrollExerciseInTraining(trainingId: String, exerciseId: String)
}