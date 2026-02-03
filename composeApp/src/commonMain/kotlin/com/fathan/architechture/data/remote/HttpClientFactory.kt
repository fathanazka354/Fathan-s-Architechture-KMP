package com.fathan.architechture.data.remote

import com.fathan.architechture.domain.repository.LocalSettings
import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object NetworkConfig {
    const val BASE_URL = "https://api.anda.com"
}

fun createHttpClient(localSettings: LocalSettings): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(Auth) {
            bearer {
                loadTokens {
                    // Ambil token dari local storage jika ada
                    // BearerTokens(accessToken, refreshToken)
                    null
                }
                
                refreshTokens {
                    // Logika refresh token global di sini
                    null
                }

                sendWithoutRequest { request ->
                    // Endpoint yang TIDAK butuh token
                    !request.url.encodedPath.endsWith("/login")
                }
            }
        }
    }
}
