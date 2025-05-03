package com.artemissoftware.calliopenotes.security.jwt

internal object JwtConstants {
    const val TYPE = "type"
    const val ACCESS = "access"
    const val REFRESH = "refresh"
    const val BEARER = "Bearer "
    const val AUTHORIZATION = "Authorization"

    const val ACCESS_TOKEN_VALIDITY_MS = 15L * 60L * 1000L //15 minutes
    const val REFRESH_TOKEN_VALIDITY_MS = 30L * 24 * 60 * 60 * 1000L //30 days
}