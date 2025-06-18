package com.yourmusic.yourmusicserver.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 요청 DTO")
data class LoginRequestDTO (
    val username: String,
    val password: String
)