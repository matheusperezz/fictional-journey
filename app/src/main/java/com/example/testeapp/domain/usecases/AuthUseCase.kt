package com.example.testeapp.domain.usecases

import com.example.testeapp.domain.entities.User

interface AuthUseCase {
  suspend fun register(user: User): Result<User>
  suspend fun login(email: String, password: String): Result<User>
}