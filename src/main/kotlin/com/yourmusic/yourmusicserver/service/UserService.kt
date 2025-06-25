package com.yourmusic.yourmusicserver.service

import com.yourmusic.yourmusicserver.entity.User
import com.yourmusic.yourmusicserver.dto.LoginRequestDTO
import com.yourmusic.yourmusicserver.dto.RegisterRequestDTO
import com.yourmusic.yourmusicserver.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun register(requestDTO: RegisterRequestDTO): Boolean {
        if (userRepository.existsByEmail(requestDTO.email)) {
            throw IllegalArgumentException("이메일이 존재합니다.")
        }
        if (userRepository.existsByUsername(requestDTO.username)) {  // 메서드명 변경
            throw IllegalArgumentException("이미 존재하는 아이디입니다.")
        }
        val encodedPassword = passwordEncoder.encode(requestDTO.password)
        val user = User(
            id = userRepository.count(),
            userUsername = requestDTO.username,
            userPassword = encodedPassword,
            email = requestDTO.email,
            name = requestDTO.name
        )
        userRepository.save(user)
        return true
    }

    fun login(requestDTO: LoginRequestDTO): Boolean {
        val user = userRepository.findByUsername(requestDTO.username)  // 메서드명 변경
        return user?.let {
            passwordEncoder.matches(requestDTO.password, it.userPassword)  // 프로퍼티명 변경
        } ?: false
    }
}