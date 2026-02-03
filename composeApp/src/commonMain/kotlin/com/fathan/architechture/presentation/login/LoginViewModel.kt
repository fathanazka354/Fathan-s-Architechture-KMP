package com.fathan.architechture.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fathan.architechture.domain.usecase.GetAuthStateUseCase
import com.fathan.architechture.domain.usecase.LoginUseCase
import com.fathan.architechture.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getAuthStateUseCase: GetAuthStateUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.LoggedOut)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAuthStateUseCase().collect {
                _uiState.value = if (it?.isLoggedIn == true) {
                    LoginUiState.LoggedIn(it.username)
                } else {
                    LoginUiState.LoggedOut
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginUseCase(username, password)
                .onSuccess {
                    _uiState.value = LoginUiState.LoggedIn(it.username)
                }
                .onFailure {
                    _uiState.value = LoginUiState.Error(it.message ?: "An unexpected error occurred")
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}

sealed class LoginUiState {
    data class LoggedIn(val username: String) : LoginUiState()
    object LoggedOut : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
