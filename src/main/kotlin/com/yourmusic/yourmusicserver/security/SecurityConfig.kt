package com.yourmusic.yourmusicserver.security

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Slf4j
@RequiredArgsConstructor
@Configuration // 현재 클래스를 설정 클래스로
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder { // 패스워드 암호화?
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.getAuthenticationManager()
        /* BCrypt : 기본으로 사용. 가장 많이 사용되는 알고리즘.
        *SCrypt : 개발자가 직접 필요에 따라 변경 가능.
        * Argon2
        * PBKDF2
        * MD5
        * SHA-1, SHA-256 등
        */
    }


    /*
 * HTTP에 대해서 '인증'과 '인가'를 담당하는 메서드
 * 필터를 통해 인증 방식과 인증 절차에 대해서 등록하며 설정을 담당하는 메서드
 * */
    // 인증은 누구인지, 인가는 들어올 수 있는지

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }

            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth/login", "/auth/register").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN") // 내부적으로 "ROLE_ADMIN"
                    .anyRequest().permitAll()
            }


            .logout {
                it
                    .logoutUrl("/logout")
                    .logoutSuccessHandler { request, response, authentication ->
                        response.sendRedirect("/")
                    }
                    .deleteCookies("jwtToken")
            }

        return http.build()
    }
}