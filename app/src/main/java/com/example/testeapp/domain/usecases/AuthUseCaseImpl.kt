package com.example.testeapp.domain.usecases

import com.example.testeapp.domain.entities.User
import com.example.testeapp.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
  private val authRepository: AuthRepository,
): AuthUseCase {
  override suspend fun register(user: User): Result<User> {
    return authRepository.register(user)
  }

  override suspend fun login(email: String, password: String): Result<User> {
    return authRepository.login(email, password)
  }
}