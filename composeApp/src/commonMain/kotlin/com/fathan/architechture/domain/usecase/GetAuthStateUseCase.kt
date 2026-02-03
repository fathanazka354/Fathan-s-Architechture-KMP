package com.fathan.architechture.domain.usecase

import com.fathan.architechture.domain.repository.AuthRepository
import com.fathan.architechture.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetAuthStateUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<User?> = authRepository.getAuthState()
}
