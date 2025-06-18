package com.yourmusic.yourmusicserver.service

import com.yourmusic.yourmusicserver.dto.SignupRequestDTO
import com.yourmusic.yourmusicserver.entity.User
import com.yourmusic.yourmusicserver.repository.UserRepository
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
