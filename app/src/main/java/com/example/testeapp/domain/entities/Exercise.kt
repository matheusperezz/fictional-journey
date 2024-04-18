package com.example.testeapp.domain.entities

data class Exercise(
  val id: String = "",
  val name: String = "",
  val image: String = "",
  val observations: String = ""
)

fun exerciseToHashMap(exercise: Exercise): HashMap<String, Any> {
  return hashMapOf(
    "name" to exercise.name,
    "image" to exercise.image,
    "observations" to exercise.observations
  )
}