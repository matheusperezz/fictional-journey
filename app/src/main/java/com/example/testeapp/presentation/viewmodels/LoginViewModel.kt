package com.example.testeapp.presentation.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.usecases.AuthUseCase
import com.example.testeapp.utils.SharedPrefManager
import com.example.testeapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
  var email: String = "",
  var password: String = "",

  val onEmailChange: (String) -> Unit = {},
  val onPasswordChange: (String) -> Unit = {}
)

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val useCase: AuthUseCase
) : ViewModel() {

  private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
  private val _loginState = MutableStateFlow(false)
  private val _loginAttempted = MutableStateFlow(true)
  val uiState = _uiState.asStateFlow()
  val loginState = _loginState.asStateFlow()
  val loginAttempted = _loginAttempted.asStateFlow()

  init {
    _uiState.value = LoginUiState(
      onEmailChange = { email ->
        _uiState.value = _uiState.value.copy(email = email)
      },
      onPasswordChange = { password ->
        _uiState.value = _uiState.value.copy(password = password)
      }
    )
  }

  fun login(context: Context) {
    _loginAttempted.value = true
    viewModelScope.launch {
      val result = useCase.login(_uiState.value.email, _uiState.value.password)
      if (result.isSuccess && result.getOrNull() != null) {
        val user = result.getOrNull()
        _loginState.value = true
        Log.d(TAG, "login user id: ${user}")
        SharedPrefManager(context).saveUserId(user?.id!!)
      } else {
        _loginState.value = false
        _loginAttempted.value = false
        Toast.makeText(context, "Crendenciais inválidas", Toast.LENGTH_SHORT).show()
      }
    }
  }

}