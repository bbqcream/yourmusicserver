package com.yourmusic.yourmusicserver.auth.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true, nullable = false)
    var account: String = "",

    @Column(nullable = false)
    var password: String = "",

    var username: String? = null,

    var name: String? = null,

    @Column(unique = true)
    var email: String? = null
) {
    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var roles: MutableList<Authority> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            field.forEach { it.user = this }
        }
}