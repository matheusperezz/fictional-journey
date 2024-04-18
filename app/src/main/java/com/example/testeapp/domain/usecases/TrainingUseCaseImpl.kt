package com.example.testeapp.domain.usecases

import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingUseCaseImpl @Inject constructor(
  private val trainingRepository: TrainingRepository
): TrainingUseCase{
  override suspend fun addTraining(training: Training): String {
    return trainingRepository.addTraining(training)
  }

  override suspend fun getTrainings(): Flow<List<Training>> {
    return trainingRepository.getTrainings()
  }

  override suspend fun getTrainingById(id: String): Training {
    return trainingRepository.getTrainingById(id)
  }

  override suspend fun deleteTraining(id: String): String {
    return trainingRepository.deleteTraining(id)
  }

  override suspend fun updateTraining(training: Training): String {
    return trainingRepository.updateTraining(training)
  }

  override suspend fun getExercisesFromTrainig(trainingId: String): Flow<List<Exercise>> {
    return trainingRepository.getExercisesFromTrainig(trainingId)
  }

  override suspend fun enrollExerciseInTraining(trainingId: String, exerciseId: String) {
    trainingRepository.enrollExerciseInTraining(trainingId, exerciseId)
  }

  override suspend fun unrollExerciseInTraining(trainingId: String, exerciseId: String) {
    trainingRepository.unrollExerciseInTraining(trainingId, exerciseId)
  }
}