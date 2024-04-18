package com.example.testeapp.domain.usecases

import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseUseCaseImpl @Inject constructor(
  private val exerciseRepository: ExerciseRepository
) : ExerciseUseCase {
  override suspend fun addExercise(exercise: Exercise): String {
    return exerciseRepository.addExercise(exercise)
  }

  override suspend fun getExercises(): Flow<List<Exercise>> {
    return exerciseRepository.getExercises()
  }

  override suspend fun getExerciseById(id: String): Exercise {
    return exerciseRepository.getExerciseById(id)
  }

  override suspend fun deleteExercise(id: String): String {
    return exerciseRepository.deleteExercise(id)
  }

  override suspend fun updateExercise(exercise: Exercise): String {
    return exerciseRepository.updateExercise(exercise)
  }
}