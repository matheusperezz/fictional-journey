package com.example.testeapp.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testeapp.presentation.navigation.auth.navigateToRegisterScreen
import com.example.testeapp.presentation.navigation.navigateToMainGraph
import com.example.testeapp.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
  viewModel: LoginViewModel = hiltViewModel(),
  navHostController: NavHostController
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val loginState by viewModel.loginState.collectAsStateWithLifecycle()
  val loginAttempted by viewModel.loginAttempted.collectAsStateWithLifecycle()

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = "Tela de login")

    OutlinedTextField(
      value = uiState.email,
      label = { Text(text = "Email") },
      keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Email
      ),
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

    Button(onClick = {
      viewModel.login(navHostController.context)
    }) {
      Text(text = "Entrar")
    }
    TextButton(onClick = { navHostController.navigateToRegisterScreen() }) {
      Text(text = "Criar uma conta")
    }

    if (loginState && loginAttempted) {
      navHostController.navigateToMainGraph()
    }
  }
}