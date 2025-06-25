package com.yourmusic.yourmusicserver.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원가입 요청 DTO")
data class RegisterRequestDTO (
    val email: String,
    val password: String,
    val name: String,
    val username: String,
)