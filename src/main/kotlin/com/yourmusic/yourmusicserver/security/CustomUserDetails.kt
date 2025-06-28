package com.yourmusic.yourmusicserver.security

import com.yourmusic.yourmusicserver.auth.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(private val user: User) : UserDetails {

    fun getMember(): User = user

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.roles?.filterNotNull()?.mapNotNull { role ->
            role.name?.let { SimpleGrantedAuthority(it) }
        }?.toMutableList() ?: mutableListOf()
    }

    override fun getUsername(): String {
        return user.account ?: ""
    }

    override fun getPassword(): String {
        return user.password ?: ""
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}