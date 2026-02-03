package com.fathan.architechture.data

import com.fathan.architechture.domain.repository.LocalSettings

expect class SettingsFactory() {
    fun create(): LocalSettings
}
