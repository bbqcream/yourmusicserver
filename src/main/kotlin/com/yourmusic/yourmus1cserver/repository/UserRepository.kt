package com.yourmusic.yourmus1cserver.repository

import com.yourmusic.yourmus1cserver.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> { // User 엔티티를 위한 데이터 접근 객체역할
    fun existsByEmail(email: String): Boolean; // 해당 이메일이 이미 DB에 존재하는지 여부를 Boolean 값으로 반환
    fun existsByUsername(username: String): Boolean;
}