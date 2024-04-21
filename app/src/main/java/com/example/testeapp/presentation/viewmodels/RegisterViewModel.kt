package com.example.testeapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeapp.domain.entities.User
import com.example.testeapp.domain.usecases.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
  var name: String = "",
  var profilePicture: String = "",
  var email: String = "",
  var password: String = "",

  val onNameChange: (String) -> Unit = {},
  val onProfilePictureChange: (String) -> Unit = {},
  val onEmailChange: (String) -> Unit = {},
  val onPasswordChange: (String) -> Unit = {}
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val useCase: AuthUseCase
) : ViewModel() {

  private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(
    RegisterUiState()
  )
  val uiState = _uiState.asStateFlow()

  init {
    _uiState.value = RegisterUiState(
      onNameChange = { name ->
        _uiState.value = _uiState.value.copy(name = name)
      },
      onProfilePictureChange = { profilePicture ->
        _uiState.value = _uiState.value.copy(profilePicture = profilePicture)
      },
      onEmailChange = { email ->
        _uiState.value = _uiState.value.copy(email = email)
      },
      onPasswordChange = { password ->
        _uiState.value = _uiState.value.copy(password = password)
      }
    )
  }

  fun register() {
    val user = User(
      name = uiState.value.name,
      profilePicture = uiState.value.profilePicture,
      email = uiState.value.email,
      password = uiState.value.password
    )

    viewModelScope.launch {
      useCase.register(user)
    }
  }

}