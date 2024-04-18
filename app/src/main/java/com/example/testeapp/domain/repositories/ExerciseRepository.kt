package com.example.testeapp.domain.repositories

import com.example.testeapp.domain.entities.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
  suspend fun addExercise(exercise: Exercise): String
  suspend fun getExercises(): Flow<List<Exercise>>
  suspend fun getExerciseById(id: String): Exercise
  suspend fun deleteExercise(id: String): String
  suspend fun updateExercise(exercise: Exercise): String
}