package com.example.testeapp.data.datamappers

import com.example.testeapp.data.datasources.RemoteTrainingDataSource
import com.example.testeapp.domain.entities.Training
import com.example.testeapp.domain.entities.TrainingPresentation
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TrainingDataMapper @Inject constructor(
  private val remoteTrainingDataSource: RemoteTrainingDataSource,
){
  suspend fun mapToPresentation(training: Training): TrainingPresentation {
    val exercises = remoteTrainingDataSource.getExercisesFromTrainig(training.id).first()
    return TrainingPresentation(
      id = training.id,
      name = training.name,
      description = training.description,
      date = training.date.toDate(),
      exercises = exercises
    )
  }
}