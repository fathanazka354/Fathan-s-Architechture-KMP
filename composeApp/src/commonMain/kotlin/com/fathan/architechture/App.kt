package com.fathan.architechture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fathan.architechture.data.AuthRepositoryImpl
import com.fathan.architechture.domain.usecase.GetAuthStateUseCase
import com.fathan.architechture.domain.usecase.LoginUseCase
import com.fathan.architechture.domain.usecase.LogoutUseCase
import com.fathan.architechture.presentation.calculator.CalculatorScreen
import com.fathan.architechture.presentation.calculator.CalculatorViewModel
import com.fathan.architechture.presentation.login.LoginScreen
import com.fathan.architechture.presentation.login.LoginUiState
import com.fathan.architechture.presentation.login.LoginViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val authRepository = remember { AuthRepositoryImpl() }
            val loginUseCase = remember { LoginUseCase(authRepository) }
            val logoutUseCase = remember { LogoutUseCase(authRepository) }
            val getAuthStateUseCase = remember { GetAuthStateUseCase(authRepository) }
            
            val loginViewModel: LoginViewModel = viewModel {
                LoginViewModel(loginUseCase, logoutUseCase, getAuthStateUseCase)
            }
            val calculatorViewModel: CalculatorViewModel = viewModel {
                CalculatorViewModel()
            }
            
            val loginState by loginViewModel.uiState.collectAsState()
            
            Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
                when (val state = loginState) {
                    is LoginUiState.LoggedIn -> {
                        CalculatorScreen(
                            state = calculatorViewModel.state,
                            onAction = calculatorViewModel::onAction,
                            onLogout = loginViewModel::logout,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.DarkGray)
                        )
                    }
                    else -> {
                        LoginScreen(
                            state = state,
                            onLogin = loginViewModel::login
                        )
                    }
                }
            }
        }
    }
}

