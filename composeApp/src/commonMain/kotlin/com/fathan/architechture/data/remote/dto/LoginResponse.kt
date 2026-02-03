package com.fathan.architechture.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val username: String
)
