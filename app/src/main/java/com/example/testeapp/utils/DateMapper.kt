package com.example.testeapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateMapper(date: Date): String{
  val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
  return dateFormat.format(date)
}