package com.example.testeapp.data.repositories

import com.example.testeapp.data.datasources.RemoteTrainingDataSource
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
  private val remoteTrainingDataSource: RemoteTrainingDataSource
): TrainingRepository {
  override suspend fun addTraining(training: Training): String {
    return remoteTrainingDataSource.addTraining(training)
  }

  override suspend fun getTrainings(): Flow<List<Training>> {
    return remoteTrainingDataSource.getTrainings()
  }

  override suspend fun getTrainingById(id: String): Training {
    return remoteTrainingDataSource.getTrainingById(id)
  }

  override suspend fun deleteTraining(id: String): String {
    return remoteTrainingDataSource.deleteTraining(id)
  }

  override suspend fun updateTraining(training: Training): String {
    return remoteTrainingDataSource.updateTraining(training)
  }

  override suspend fun getExercisesFromTrainig(trainingId: String): Flow<List<Exercise>> {
    return remoteTrainingDataSource.getExercisesFromTrainig(trainingId)
  }

  override suspend fun enrollExerciseInTraining(trainingId: String, exerciseId: String) {
    remoteTrainingDataSource.enrollExerciseInTraining(trainingId, exerciseId)
  }

  override suspend fun unrollExerciseInTraining(trainingId: String, exerciseId: String) {
    remoteTrainingDataSource.unrollExerciseInTraining(trainingId, exerciseId)
  }

}