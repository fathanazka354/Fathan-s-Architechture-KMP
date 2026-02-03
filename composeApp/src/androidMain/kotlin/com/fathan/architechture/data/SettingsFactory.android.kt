package com.fathan.architechture.data

import android.content.Context
import com.fathan.architechture.domain.repository.LocalSettings
import java.lang.IllegalStateException

actual class SettingsFactory {
    actual fun create(): LocalSettings {
        if (applicationContext == null) {
            throw IllegalStateException("Context not initialized. Call SettingsFactory.init(context) in your Application class or MainActivity.")
        }
        return AndroidLocalSettings(applicationContext!!)
    }

    companion object {
        private var applicationContext: Context? = null
        fun init(context: Context) {
            applicationContext = context.applicationContext
        }
    }
}

class AndroidLocalSettings(context: Context) : LocalSettings {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun saveUsername(username: String?) {
        if (username == null) {
            prefs.edit().remove("username").apply()
        } else {
            prefs.edit().putString("username", username).apply()
        }
    }

    override fun getUsername(): String? = prefs.getString("username", null)
}
