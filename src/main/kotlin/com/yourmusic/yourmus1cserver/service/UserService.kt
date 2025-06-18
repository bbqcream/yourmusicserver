package com.yourmusic.yourmus1cserver.service

import com.yourmusic.yourmus1cserver.dto.SignupRequestDTO
import com.yourmusic.yourmus1cserver.entity.User
import com.yourmusic.yourmus1cserver.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun signup(requestDTO: SignupRequestDTO): User {
        if (userRepository.existsByEmail(requestDTO.email)) {
            throw IllegalArgumentException("이메일이 존재합니다.")
        }
        if (userRepository.existsByUsername(requestDTO.username)) {
            throw IllegalArgumentException("이미 존재하는 아이디입니다.")
        }
        val encodedPassword = passwordEncoder.encode(requestDTO.password)
        val user = User(
            username = requestDTO.username,
            password = encodedPassword,
            email = requestDTO.email,
            name = requestDTO.name
        )
        return userRepository.save(user)
    }
}
