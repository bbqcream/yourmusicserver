package com.yourmusic.yourmusicserver.auth.dto

import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class SignRequest(
    var id: Long? = null,
    var account: String = "",
    var username: String? = null,
    var password: String? = null,
    var name: String? = null,
    var email: String? = null
)