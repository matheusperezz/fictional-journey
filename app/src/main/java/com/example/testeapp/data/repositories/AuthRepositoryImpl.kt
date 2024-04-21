package com.example.testeapp.data.repositories

import com.example.testeapp.data.datasources.RemoteAuthDataSource
import com.example.testeapp.domain.entities.User
import com.example.testeapp.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val remoteAuthDataSource: RemoteAuthDataSource
): AuthRepository {
  override suspend fun register(user: User): Result<User> {
    return remoteAuthDataSource.register(user)
  }

  override suspend fun login(email: String, password: String): Result<User> {
    return remoteAuthDataSource.login(email, password)
  }
}