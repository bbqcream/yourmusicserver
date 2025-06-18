package com.yourmusic.yourmus1cserver.dto

data class SignupRequestDTO (
    val email: String,
    val password: String,
    val name: String,
    val username: String,
)