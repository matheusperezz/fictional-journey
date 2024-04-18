package com.example.testeapp.data.repositories

import com.example.testeapp.data.datasources.RemoteExerciseDataSource
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
  private val remoteExerciseDataSource: RemoteExerciseDataSource
) : ExerciseRepository {
  override suspend fun addExercise(exercise: Exercise): String {
    return remoteExerciseDataSource.addExercise(exercise)
  }

  override suspend fun getExercises(): Flow<List<Exercise>> {
    return remoteExerciseDataSource.getExercises()
  }

  override suspend fun getExerciseById(id: String): Exercise {
    return remoteExerciseDataSource.getExerciseById(id)
  }

  override suspend fun deleteExercise(id: String): String {
    return remoteExerciseDataSource.deleteExercise(id)
  }

  override suspend fun updateExercise(exercise: Exercise): String {
    return remoteExerciseDataSource.updateExercise(exercise)
  }
}