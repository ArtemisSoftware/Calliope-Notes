package com.artemissoftware.calliopenotes.controllers.auth

import com.artemissoftware.calliopenotes.controllers.auth.models.AuthRequest
import com.artemissoftware.calliopenotes.controllers.auth.models.RefreshRequest
import com.artemissoftware.calliopenotes.security.auth.AuthService
import com.artemissoftware.calliopenotes.security.auth.TokenPair
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: AuthRequest
    ) {
        authService.register(body.email, body.password)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: AuthRequest
    ): TokenPair {
        return authService.login(body.email, body.password)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): TokenPair {
        return authService.refresh(body.refreshToken)
    }
}