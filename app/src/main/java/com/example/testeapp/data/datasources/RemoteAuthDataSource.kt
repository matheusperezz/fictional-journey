package com.example.testeapp.data.datasources

import com.example.testeapp.domain.entities.User

interface RemoteAuthDataSource {
  suspend fun register(user: User): Result<User>
  suspend fun login(email: String, password: String): Result<User>
}