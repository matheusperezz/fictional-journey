package com.example.testeapp.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.navigation.auth.navigateToAuthGraph
import com.example.testeapp.presentation.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
  viewModel: RegisterViewModel = hiltViewModel(),
  navHostController: NavHostController
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Column(
    Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Text(text = "Cadastre-se")

    OutlinedTextField(
      value = uiState.name,
      label = { Text(text = "Nome") },
      onValueChange = uiState.onNameChange,
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
      value = uiState.email,
      label = { Text(text = "Email") },
      onValueChange = uiState.onEmailChange,
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
      value = uiState.password,
      label = { Text(text = "Senha") },
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Password
      ),
      onValueChange = uiState.onPasswordChange,
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
      value = uiState.profilePicture,
      label = { Text(text = "Imagem de perfil") },
      onValueChange = uiState.onProfilePictureChange,
      modifier = Modifier.fillMaxWidth()
    )

    Button(onClick = {
      viewModel.register()
      navHostController.navigateToAuthGraph()
      Toast.makeText(null, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
    }) {
      Text(text = "Registrar")
    }
  }
}