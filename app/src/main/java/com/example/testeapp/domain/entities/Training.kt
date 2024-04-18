package com.example.testeapp.domain.entities

import com.google.firebase.Timestamp

data class Training(
  val id: String = "",
  val name: String = "",
  val description: String = "",
  val date: Timestamp = Timestamp.now(),
  val exercises: List<String> = emptyList()
)

fun trainingToHashMap(training: Training): HashMap<String, Any> {
  return hashMapOf(
    "name" to training.name,
    "description" to training.description,
    "date" to training.date,
    "exercises" to training.exercises
  )
}
