package com.example.testeapp.domain.repositories

import com.example.testeapp.domain.entities.User

interface AuthRepository {
  suspend fun register(user: User): Result<User>
  suspend fun login(email: String, password: String): Result<User>
}