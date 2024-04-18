package com.example.testeapp.data.datasources

import android.util.Log
import com.example.testeapp.domain.entities.Exercise
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.entities.trainingToHashMap
import com.example.testeapp.utils.EXERCISE_COLLECTION
import com.example.testeapp.utils.TAG
import com.example.testeapp.utils.TRAINING_COLLECTION
import com.example.testeapp.utils.TRAINING_EXERCISE_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteTrainingDataSourceImpl @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val remoteExerciseDataSource: RemoteExerciseDataSource
): RemoteTrainingDataSource {
  override suspend fun addTraining(training: Training): String {
    val trainingMap = trainingToHashMap(training)
    return try {
      val documentReference = firestore.collection(TRAINING_COLLECTION).add(trainingMap).await()
      documentReference.id
    } catch (e: Exception) {
      e.message.toString()
    }
  }

  override suspend fun getTrainings(): Flow<List<Training>> {
    return try {
      val trainings = firestore.collection(TRAINING_COLLECTION)
        .get()
        .await()
        .documents
        .mapNotNull { document ->
          document.toObject<Training>()?.copy(id = document.id)
        }
      flowOf(trainings)
    } catch (e: Exception) {
      Log.e(TAG, e.message.toString())
      flowOf(emptyList())
    }
  }

  override suspend fun getTrainingById(id: String): Training {
    val document = firestore.collection(TRAINING_COLLECTION).document(id).get().await()
    return document.toObject<Training>()?.copy(id = document.id) ?: Training()
  }

  override suspend fun deleteTraining(id: String): String {
    return try {
      firestore.collection(TRAINING_COLLECTION).document(id).delete().await()
      "Training deleted successfully"
    } catch (e: Exception) {
      e.message.toString()
    }
  }

  override suspend fun updateTraining(training: Training): String {
    val trainingMap = trainingToHashMap(training)
    return try {
      firestore.collection(TRAINING_COLLECTION).document(training.id).set(trainingMap).await()
      "Training updated successfully"
    } catch (e: Exception) {
      e.message.toString()
    }
  }

  override suspend fun getExercisesFromTrainig(trainingId: String): Flow<List<Exercise>> {
    return flow {
      val exercises = mutableListOf<Exercise>()
      val exerciseIds = firestore.collection(TRAINING_EXERCISE_COLLECTION)
        .whereEqualTo("trainingId", trainingId)
        .get()
        .await()
        .documents
        .map { it.getString("exerciseId") ?: "" }
      Log.d(TAG, "getExercisesFromTrainig: $exerciseIds")
      for (id in exerciseIds) {
        val exercise = remoteExerciseDataSource.getExerciseById(id)
        exercises.add(exercise)
      }
      Log.d(TAG, "getExercisesFromTrainig: $exercises")
      emit(exercises)
    }
  }

  override suspend fun enrollExerciseInTraining(trainingId: String, exerciseId: String) {
    val data = hashMapOf(
      "trainingId" to trainingId,
      "exerciseId" to exerciseId
    )
    firestore.collection(TRAINING_EXERCISE_COLLECTION).add(data).await()
  }

  override suspend fun unrollExerciseInTraining(trainingId: String, exerciseId: String) {
    val document = firestore.collection(TRAINING_EXERCISE_COLLECTION)
      .whereEqualTo("trainingId", trainingId)
      .whereEqualTo("exerciseId", exerciseId)
      .get()
      .await()
      .documents
      .firstOrNull()

    if (document != null) {
      firestore.collection(TRAINING_EXERCISE_COLLECTION).document(document.id).delete().await()
    }
  }
}