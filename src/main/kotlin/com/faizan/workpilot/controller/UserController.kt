package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateUserRequest
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.dto.response.UserResponse
import com.faizan.workpilot.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(
        @Valid
        @RequestBody request: CreateUserRequest
    ): ResponseEntity<SuccessResponse<UserResponse>> {

        val savedUser = userService.createUser(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            SuccessResponse(
                message = "User created successfully",
                data = savedUser
            )
        )
    }

}