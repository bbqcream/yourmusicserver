package com.yourmusic.yourmusicserver.auth.service

import com.yourmusic.yourmusicserver.auth.dto.SignRequest
import com.yourmusic.yourmusicserver.auth.dto.SignResponse
import com.yourmusic.yourmusicserver.auth.entity.Authority
import com.yourmusic.yourmusicserver.auth.entity.User
import com.yourmusic.yourmusicserver.auth.repository.UserRepository
import com.yourmusic.yourmusicserver.security.JwtProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class SignService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
) {
    private val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Throws(Exception::class)
    fun login(request: SignRequest): SignResponse {
        val user: User = userRepository.findByAccount(request.account!!)
            .orElseThrow { BadCredentialsException("잘못된 계정정보입니다.") }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw BadCredentialsException("잘못된 계정정보입니다.")
        }

        return SignResponse(
            id = user.id,
            account = user.account,
            username = user.username,
            name = user.name,
            email = user.email,
            roles = user.roles,
            token = jwtProvider.createJwt(user.account, user.roles)
        )
    }

    @Throws(Exception::class)
    fun register(request: SignRequest): Boolean {
        try {
            val user = User(
                account = request.account ?: throw IllegalArgumentException("account is required"),
                password = passwordEncoder.encode(request.password ?: throw IllegalArgumentException("password is required")),
                username = request.username,
                name = request.name,
                email = request.email
            )

            // 역할 설정
            val role = Authority(name = "ROLE_USER")
            role.user = user
            user.roles = mutableListOf(role)

            userRepository.save(user)
        } catch (e: Exception) {
            println(e.message)
            throw Exception("잘못된 요청입니다.")
        }
        return true
    }

    @Throws(Exception::class)
    fun getUser(account: String): SignResponse {
        val user: User = userRepository.findByAccount(account)
            .orElseThrow{ Exception("계정을 찾을 수 없습니다.") }
        return SignResponse(user)
    }

}
