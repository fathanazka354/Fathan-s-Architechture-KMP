package com.fathan.architechture

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform