package com.yourmusic.yourmusicserver.controller

import com.yourmusic.yourmusicserver.dto.LoginRequestDTO
import com.yourmusic.yourmusicserver.dto.RegisterRequestDTO
import com.yourmusic.yourmusicserver.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController (
    private val userService: UserService,
){
    @Operation(summary = "회원가입", description = "이메일, 비밀번호를 통한 회원가입 요청")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: RegisterRequestDTO) :ResponseEntity<Any>{
        val created = userService.register(request)
        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED).build()
        }else{
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @Operation(summary = "로그인", description = "이메일, 비밀번호를 통한 로그인 요청")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request: LoginRequestDTO) :ResponseEntity<String>{
        val success = userService.login(request)
        return if (success) {
            ResponseEntity.ok("로그인에 성공했습니다.")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패했습니다.")
        }
    }
}