package com.fathan.architechture.domain.usecase

import com.fathan.architechture.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String) = authRepository.login(username, password)
}
