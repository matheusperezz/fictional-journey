package com.example.testeapp.domain.entities

import java.security.MessageDigest

data class User(
  var id: String = "",
  val name: String = "",
  val profilePicture: String = "",
  val email: String = "",
  val password: String = ""
)

fun hashPassword(password: String): String {
  val bytes = password.toByteArray()
  val md = MessageDigest.getInstance("SHA-256")
  val digest = md.digest(bytes)
  return digest.fold("", { str, it -> str + "%02x".format(it) })
}

fun checkPassword(password: String, hashedPassword: String): Boolean {
  val hashOfInput = hashPassword(password)
  return hashOfInput == hashedPassword
}

fun userToHashMap(user: User): HashMap<String, Any> {
  return hashMapOf(
    "name" to user.name,
    "profilePicture" to user.profilePicture,
    "email" to user.email,
    "password" to hashPassword(user.password)
  )
}
