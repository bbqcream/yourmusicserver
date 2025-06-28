package com.yourmusic.yourmusicserver.auth.repository

import com.yourmusic.yourmusicserver.auth.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByAccount(account: String): Optional<User>
}