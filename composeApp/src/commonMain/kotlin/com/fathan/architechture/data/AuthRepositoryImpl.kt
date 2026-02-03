package com.fathan.architechture.data

import com.fathan.architechture.data.remote.AuthApiService
import com.fathan.architechture.domain.model.User
import com.fathan.architechture.domain.repository.AuthRepository
import com.fathan.architechture.domain.repository.LocalSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val localSettings: LocalSettings
) : AuthRepository {

    private val _user = MutableStateFlow<User?>(
        localSettings.getUsername()?.let { User(it, true) }
    )

    override fun getAuthState(): Flow<User?> = _user

    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            // Implementasi API yang sesungguhnya:
            // val response = authApiService.login(LoginRequest(username, password))
            // localSettings.saveUsername(response.username)
            // ... dst
            
            if (password == "password") {
                val loggedInUser = User(username, true)
                localSettings.saveUsername(username)
                _user.value = loggedInUser
                Result.success(loggedInUser)
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        localSettings.saveUsername(null)
        _user.value = null
    }
}
