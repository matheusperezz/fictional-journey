package com.example.testeapp.di.modules

import android.content.Context
import com.example.testeapp.data.datasources.RemoteAuthDataSource
import com.example.testeapp.data.datasources.RemoteAuthDataSourceImpl
import com.example.testeapp.data.repositories.AuthRepositoryImpl
import com.example.testeapp.domain.repositories.AuthRepository
import com.example.testeapp.domain.usecases.AuthUseCase
import com.example.testeapp.domain.usecases.AuthUseCaseImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

  @Provides
  @Singleton
  fun provideAuthDataSource(
    firestore: FirebaseFirestore,
    @ApplicationContext context: Context
  ): RemoteAuthDataSource {
    return RemoteAuthDataSourceImpl(firestore, context)
  }

  @Provides
  @Singleton
  fun provideAuthRepository(remoteAuthDataSource: RemoteAuthDataSource): AuthRepository {
    return AuthRepositoryImpl(remoteAuthDataSource)
  }

  @Provides
  @Singleton
  fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCase {
    return AuthUseCaseImpl(authRepository)
  }

}