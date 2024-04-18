package com.example.testeapp.domain.usecases

import com.example.testeapp.domain.entities.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseUseCase {
  suspend fun addExercise(exercise: Exercise): String
  suspend fun getExercises(): Flow<List<Exercise>>
  suspend fun getExerciseById(id: String): Exercise
  suspend fun deleteExercise(id: String): String
  suspend fun updateExercise(exercise: Exercise): String
}