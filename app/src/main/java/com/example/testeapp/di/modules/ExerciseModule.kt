package com.example.testeapp.di.modules

import com.example.testeapp.data.datasources.RemoteExerciseDataSource
import com.example.testeapp.data.datasources.RemoteExerciseDataSourceImpl
import com.example.testeapp.data.repositories.ExerciseRepositoryImpl
import com.example.testeapp.domain.repositories.ExerciseRepository
import com.example.testeapp.domain.usecases.ExerciseUseCase
import com.example.testeapp.domain.usecases.ExerciseUseCaseImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExerciseModule {

  @Provides
  @Singleton
  fun provideRemoteExerciseDataSource(firestore: FirebaseFirestore): RemoteExerciseDataSource {
    return RemoteExerciseDataSourceImpl(firestore)
  }

  @Provides
  @Singleton
  fun provideExerciseRepository(dataSource: RemoteExerciseDataSource): ExerciseRepository {
    return ExerciseRepositoryImpl(dataSource)
  }

  @Provides
  @Singleton
  fun provideExerciseUseCase(repository: ExerciseRepository): ExerciseUseCase {
    return ExerciseUseCaseImpl(repository)
  }

}