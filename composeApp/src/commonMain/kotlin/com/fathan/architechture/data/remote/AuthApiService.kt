package com.fathan.architechture.data.remote

import com.fathan.architechture.data.remote.dto.LoginRequest
import com.fathan.architechture.data.remote.dto.LoginResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

interface AuthApiService {
    suspend fun login(request: LoginRequest): LoginResponse
}

class AuthApiServiceImpl(private val client: HttpClient) : AuthApiService {
    override suspend fun login(request: LoginRequest): LoginResponse {
        return client.post {
            url("${NetworkConfig.BASE_URL}/login")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
