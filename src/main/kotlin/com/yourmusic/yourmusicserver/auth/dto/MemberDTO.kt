package com.yourmusic.yourmusicserver.auth.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class MemberDTO (
    val email: String,
    val password: String,
    val name: String,
    val username: String,
    val auth: String,
    val enable: Int
    )