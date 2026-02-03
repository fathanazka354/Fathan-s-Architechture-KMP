package com.fathan.architechture.domain.repository

import com.fathan.architechture.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAuthState(): Flow<User?>
    suspend fun login(username: String, password: String): Result<User>
    suspend fun logout()
}
