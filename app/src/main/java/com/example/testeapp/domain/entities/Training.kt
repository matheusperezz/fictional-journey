package com.example.testeapp.domain.entities

import com.google.firebase.Timestamp
import java.util.Date

data class Training(
  val id: String = "",
  val name: String = "",
  val description: String = "",
  val date: Timestamp = Timestamp.now(),
  val userId: String = ""
)

data class TrainingPresentation(
  val id: String = "",
  val name: String = "",
  val description: String = "",
  val date: Date = Date(),
  val exercises: List<Exercise> = emptyList(),
  val userId: String = ""
)

fun trainingToHashMap(training: Training): HashMap<String, Any> {
  return hashMapOf(
    "name" to training.name,
    "description" to training.description,
    "date" to training.date,
    "userId" to training.userId
  )
}
