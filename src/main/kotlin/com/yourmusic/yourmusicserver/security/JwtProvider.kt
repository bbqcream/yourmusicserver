package com.yourmusic.yourmusicserver.security

import com.yourmusic.yourmusicserver.auth.entity.Authority
import com.yourmusic.yourmusicserver.auth.service.JpaUserDetailsService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtProvider(
) {
    private val userDetailsService: JpaUserDetailsService = JpaUserDetailsService()

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private lateinit var secretKey: Key
    private val expirationTime = 3600000L // 1 hour in milliseconds

    @PostConstruct
    fun init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    // 토큰 생성
    fun createJwt(account: String, roles: MutableList<Authority>): String {
        val claims = Jwts.claims().setSubject(account)
        claims["roles"] = roles
        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getAccount(token: String): String {
        val cleanToken = extractTokenFromBearer(token)
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(cleanToken)
            .body
            .subject
    }

    // 권한 정보 획득
    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(this.getAccount(token))
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    // Authorization Header를 통해 인증을 한다.
    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    fun validateToken(token: String): Boolean {
        return try {
            val cleanToken = extractTokenFromBearer(token)
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(cleanToken)

            // 만료되었을 시 false
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    private fun extractTokenFromBearer(token: String): String {
        return if (token.startsWith("Bearer ", ignoreCase = true)) {
            token.substring(7).trim()
        } else {
            token
        }
    }
}