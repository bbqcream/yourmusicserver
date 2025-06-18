package com.yourmusic.yourmusicserver.dto

data class SignupRequestDTO (
    val email: String,
    val password: String,
    val name: String,
    val username: String,
)