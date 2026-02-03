package com.fathan.architechture.data

import com.fathan.architechture.domain.repository.LocalSettings
import platform.Foundation.NSUserDefaults

actual class SettingsFactory {
    actual fun create(): LocalSettings = IosLocalSettings()
}

class IosLocalSettings : LocalSettings {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun saveUsername(username: String?) {
        if (username == null) {
            userDefaults.removeObjectForKey("username")
        } else {
            userDefaults.setObject(username, "username")
        }
    }

    override fun getUsername(): String? = userDefaults.stringForKey("username")
}
