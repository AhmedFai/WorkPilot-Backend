package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "Email should not be empty")
    @field:Email(message = "Invalid email")
    val email: String,

    @field:NotBlank(message = "Password should not be empty")
    @field:Size(
        min = 8,
        max = 14,
        message = "Password must be between 8 and 14 characters"
    )
    val password: String
)
