package com.example.testeapp.di.modules

import android.content.Context
import com.example.testeapp.data.datasources.RemoteExerciseDataSource
import com.example.testeapp.data.datasources.RemoteTrainingDataSource
import com.example.testeapp.data.datasources.RemoteTrainingDataSourceImpl
import com.example.testeapp.data.repositories.TrainingRepositoryImpl
import com.example.testeapp.domain.repositories.TrainingRepository
import com.example.testeapp.domain.usecases.TrainingUseCase
import com.example.testeapp.domain.usecases.TrainingUseCaseImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrainingModule {

  @Provides
  @Singleton
  fun provideRemoteTrainingDataSource(
    firestore: FirebaseFirestore,
    remoteExerciseDataSource: RemoteExerciseDataSource,
    @ApplicationContext context: Context
  ): RemoteTrainingDataSource {
    return RemoteTrainingDataSourceImpl(firestore, remoteExerciseDataSource, context)
  }

  @Provides
  @Singleton
  fun provideTrainingRepository(dataSource: RemoteTrainingDataSource): TrainingRepository {
    return TrainingRepositoryImpl(dataSource)
  }

  @Provides
  @Singleton
  fun provideExerciseUseCase(repository: TrainingRepository): TrainingUseCase {
    return TrainingUseCaseImpl(repository)
  }

}