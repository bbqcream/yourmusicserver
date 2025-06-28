package com.yourmusic.yourmusicserver.auth.dto

import com.yourmusic.yourmusicserver.auth.entity.Authority
import com.yourmusic.yourmusicserver.auth.entity.User
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
data class SignResponse(
    var id: Long? = null,
    var account: String? = null,
    var username: String? = null,
    var name: String? = null,
    var email: String? = null,
    var roles: MutableList<Authority> = mutableListOf(),
    var token: String? = null
) {
    constructor(user: User) : this(
        id = user.id,
        account = user.account,
        username = user.username,
        name = user.name,
        email = user.email,
        roles = user.roles
    )
}

