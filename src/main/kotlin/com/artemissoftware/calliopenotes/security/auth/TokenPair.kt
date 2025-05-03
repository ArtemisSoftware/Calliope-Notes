package com.artemissoftware.calliopenotes.security.auth

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
