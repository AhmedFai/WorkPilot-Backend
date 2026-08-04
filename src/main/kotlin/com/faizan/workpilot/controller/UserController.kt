package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateUserRequest
import com.faizan.workpilot.dto.request.UpdateUserRequest
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.dto.response.UserResponse
import com.faizan.workpilot.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @GetMapping
    fun getAllUsers(): ResponseEntity<SuccessResponse<List<UserResponse>>> {
        val allUsers = userService.getAllUsers()

        return ResponseEntity.ok(
            SuccessResponse(
                message = "All users are fetched successfully",
                data = allUsers
            )
        )
    }

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<UserResponse>> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "User fetch successfully",
                data = user
            )
        )
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        request: UpdateUserRequest
    ): ResponseEntity<SuccessResponse<UserResponse>> {
        val updatedUser = userService.updateUser(id, request)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "User updated successfully",
                data = updatedUser
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<UserResponse>> {
        val deletedUser = userService.deleteUser(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "User deleted successfully",
                data = deletedUser
            )
        )
    }

}