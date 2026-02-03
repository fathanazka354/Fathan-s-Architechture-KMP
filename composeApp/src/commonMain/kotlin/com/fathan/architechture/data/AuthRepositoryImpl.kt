package com.fathan.architechture.data

import com.fathan.architechture.domain.model.User
import com.fathan.architechture.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepositoryImpl: AuthRepository {

    // Simulasi local storage dengan variable statis agar persistent selama app running
    companion object {
        private var savedUser: User? = null
    }

    private val user = MutableStateFlow<User?>(savedUser)

    override fun getAuthState(): Flow<User?> = user

    override suspend fun login(username: String, password: String): Result<User> {
        return if (password == "password") {
            val loggedInUser = User(username, true)
            savedUser = loggedInUser
            user.value = loggedInUser
            Result.success(loggedInUser)
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }

    override suspend fun logout() {
        savedUser = null
        user.value = null
    }
}
