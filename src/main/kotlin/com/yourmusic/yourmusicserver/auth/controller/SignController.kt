package com.yourmusic.yourmusicserver.auth.controller

import com.yourmusic.yourmusicserver.auth.dto.SignRequest
import com.yourmusic.yourmusicserver.auth.dto.SignResponse
import com.yourmusic.yourmusicserver.auth.service.SignService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class SignController(
    private val signService: SignService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: SignRequest): SignResponse {
        return signService.login(request)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: SignRequest): Boolean {
        return signService.register(request)
    }

    @GetMapping("/me")
    fun getUserForAdmin(@RequestParam account: String): ResponseEntity<SignResponse> {
        val user = signService.getUser(account)
        return ResponseEntity.ok(user)
    }
}
