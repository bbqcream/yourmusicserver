package com.yourmusic.yourmusicserver.security

import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter(private val jwtProvider: JwtProvider) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = jwtProvider.resolveToken(request)

        if (token != null && jwtProvider.validateToken(token)) {
            // check access token
            token = token.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].trim { it <= ' ' }
            val auth: Authentication = jwtProvider.getAuthentication(token)
            SecurityContextHolder.getContext().setAuthentication(auth)
        }

        filterChain.doFilter(request, response)
    }
}