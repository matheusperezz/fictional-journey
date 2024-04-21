package com.example.testeapp.utils

import android.content.Context
import android.content.SharedPreferences

const val USER_PREFERENCES = "userId"

class SharedPrefManager(context: Context) {
  private val sharedPreferences: SharedPreferences = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

  fun saveUserId(userId: String){
    val editor = sharedPreferences.edit()
    editor.putString(USER_PREFERENCES, userId)
    editor.apply()
  }

  fun getUserId(): String? {
    return sharedPreferences.getString(USER_PREFERENCES, null)
  }
}