package com.example.testeapp.domain.usecases

import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import kotlinx.coroutines.flow.Flow

interface TrainingUseCase {
  suspend fun addTraining(training: Training): String
  suspend fun getTrainings(): Flow<List<Training>>
  suspend fun getTrainingById(id: String): Training
  suspend fun deleteTraining(id: String): String
  suspend fun updateTraining(training: Training): String
  suspend fun getExercisesFromTrainig(trainingId: String): Flow<List<Exercise>>
  suspend fun enrollExerciseInTraining(trainingId: String, exerciseId: String)
  suspend fun unrollExerciseInTraining(trainingId: String, exerciseId: String)
}