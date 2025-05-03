package com.artemissoftware.calliopenotes.security.jwt

import com.artemissoftware.calliopenotes.security.jwt.JwtConstants.ACCESS
import com.artemissoftware.calliopenotes.security.jwt.JwtConstants.ACCESS_TOKEN_VALIDITY_MS
import com.artemissoftware.calliopenotes.security.jwt.JwtConstants.BEARER
import com.artemissoftware.calliopenotes.security.jwt.JwtConstants.REFRESH
import com.artemissoftware.calliopenotes.security.jwt.JwtConstants.REFRESH_TOKEN_VALIDITY_MS
import com.artemissoftware.calliopenotes.security.jwt.JwtConstants.TYPE
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Base64
import java.util.Date

@Service
class JwtService(
    @Value("\${jwt.secret}") private val jwtSecret: String
) {

    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))

    private fun generateToken(
        userId: String,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
            .subject(userId)
            .claim(TYPE, type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: String): String {
        return generateToken(userId, ACCESS, ACCESS_TOKEN_VALIDITY_MS)
    }

    fun generateRefreshToken(userId: String): String {
        return generateToken(userId, REFRESH, REFRESH_TOKEN_VALIDITY_MS)
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims[TYPE] as? String ?: return false
        return tokenType == ACCESS
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims[TYPE] as? String ?: return false
        return tokenType == REFRESH
    }

    fun getUserIdFromToken(token: String): String {
        val claims = parseAllClaims(token) ?: throw ResponseStatusException(
            HttpStatusCode.valueOf(401),
            "Invalid token."
        )
        return claims.subject
    }

    private fun parseAllClaims(token: String): Claims? {
        val rawToken = if(token.startsWith(BEARER)) {
            token.removePrefix(BEARER)
        } else token

        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch(e: Exception) {
            null
        }
    }
}