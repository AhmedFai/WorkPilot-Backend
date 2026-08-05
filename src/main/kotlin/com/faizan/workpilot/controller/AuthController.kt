package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.LoginRequest
import com.faizan.workpilot.dto.response.LoginResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        request: LoginRequest
    ): ResponseEntity<SuccessResponse<LoginResponse>> {
        val loginResponse  = authService.login(request)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Login successful",
                data = loginResponse
            )
        )
    }

}