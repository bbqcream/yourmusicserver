package com.yourmusic.yourmusicserver.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {
}