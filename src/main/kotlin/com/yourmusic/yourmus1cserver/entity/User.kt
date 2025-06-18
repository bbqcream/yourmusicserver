package com.yourmusic.yourmus1cserver.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity // 엔티티 선언하는 코드
@Table(name = "users") // 테이블 정하기
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키 정하기
    val id: Long? = null,

    @Column(nullable = false, unique = true) // 일반 요소인 거 같음, 같은 이메일로 회원가입할 수 없음
    val email: String,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column (nullable = false, unique = true)
    val username: String,

    @Column(nullable = false, unique = false)
    val password: String,
)
