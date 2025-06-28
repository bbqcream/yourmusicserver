package com.yourmusic.yourmusicserver.auth.service

import com.yourmusic.yourmusicserver.auth.entity.User
import com.yourmusic.yourmusicserver.auth.repository.UserRepository
import com.yourmusic.yourmusicserver.security.CustomUserDetails
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class JpaUserDetailsService : UserDetailsService {
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userRepository?.findByAccount(username)?.orElseThrow(
            { UsernameNotFoundException("Invalid authentication!") }
        )

        return CustomUserDetails(user!!)
    }
}