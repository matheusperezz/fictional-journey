package com.example.testeapp.data.datasources

import com.example.testeapp.domain.entities.Exercise
import kotlinx.coroutines.flow.Flow

interface RemoteExerciseDataSource {
  suspend fun addExercise(exercise: Exercise): String
  suspend fun getExercises(): Flow<List<Exercise>>
  suspend fun getExerciseById(id: String): Exercise
  suspend fun deleteExercise(id: String): String
  suspend fun updateExercise(exercise: Exercise): String
}