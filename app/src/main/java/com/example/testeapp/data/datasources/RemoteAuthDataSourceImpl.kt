package com.example.testeapp.data.datasources

import android.content.Context
import android.util.Log
import com.example.testeapp.domain.entities.User
import com.example.testeapp.domain.entities.checkPassword
import com.example.testeapp.domain.entities.userToHashMap
import com.example.testeapp.utils.SharedPrefManager
import com.example.testeapp.utils.TAG
import com.example.testeapp.utils.USER_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val context: Context
) : RemoteAuthDataSource {

  override suspend fun register(user: User): Result<User> {
    val userMap = userToHashMap(user)
    return try {
      val docRef = firestore.collection(USER_COLLECTION).add(userMap).await()
      val userId = docRef.id
      user.id = userId
      Result.success(user)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun login(email: String, password: String): Result<User> {
    return try {
      val querySnapshot = firestore.collection(USER_COLLECTION)
        .whereEqualTo("email", email)
        .get()
        .await()
      val document = querySnapshot.documents.firstOrNull()
      val user = document?.toObject<User>()
      Log.d(TAG, "login: $user")
      if (user != null && checkPassword(password, user.password)) {
        user.id = document.id
        SharedPrefManager(context).saveUserId(user.id)
        Result.success(user)
      } else {
        Result.failure(Exception("User not found"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}