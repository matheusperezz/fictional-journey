package com.example.testeapp.data.datasources

import android.util.Log
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.exerciseToHashMap
import com.example.testeapp.utils.EXERCISE_COLLECTION
import com.example.testeapp.utils.TAG
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteExerciseDataSourceImpl @Inject constructor(
  private val firestore: FirebaseFirestore
) : RemoteExerciseDataSource {
  override suspend fun addExercise(exercise: Exercise): String {
    val exerciseMap = exerciseToHashMap(exercise)
    return try {
      val documentReference =
        firestore.collection(EXERCISE_COLLECTION).add(exerciseMap).await()
      documentReference.id
    } catch (e: Exception) {
      Log.e(TAG, e.message.toString())
      e.message.toString()
    }
  }

  override suspend fun getExercises(): Flow<List<Exercise>> {
    return try {
      val exercises = firestore.collection(EXERCISE_COLLECTION)
        .get()
        .await()
        .documents
        .mapNotNull { document ->
          document.toObject<Exercise>()?.copy(id = document.id)
        }
      flowOf(exercises)
    } catch (e: Exception) {
      Log.e(TAG, e.message.toString())
      flowOf(emptyList())
    }
  }

  override suspend fun getExerciseById(id: String): Exercise {
    val document = firestore.collection(EXERCISE_COLLECTION).document(id).get().await()
    return document.toObject<Exercise>()?.copy(id = document.id) ?: Exercise()
  }

  override suspend fun deleteExercise(id: String): String {
    return try {
      firestore.collection(EXERCISE_COLLECTION).document(id).delete().await()
      "Exercise deleted successfully"
    } catch (e: Exception) {
      Log.e(TAG, e.message.toString())
      e.message.toString()
    }
  }

  override suspend fun updateExercise(exercise: Exercise): String {
    return try {
      val exerciseMap = exerciseToHashMap(exercise)
      firestore.collection(EXERCISE_COLLECTION).document(exercise.id).set(exerciseMap).await()
      "Updated successfully"
    } catch (e: Exception) {
      Log.e(TAG, e.message.toString())
      e.message.toString()
    }
  }
}