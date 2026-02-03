package com.fathan.architechture.domain.repository

interface LocalSettings {
    fun saveUsername(username: String?)
    fun getUsername(): String?
}
