package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateCompanyAdminRequest(

    @field:NotBlank(message = "First name cannot be empty")
    val firstName: String,

    @field:NotBlank(message = "Last name cannot be empty")
    val lastName: String,

    @field:NotBlank(message = "Email cannot be empty")
    @field:Email(message = "Invalid email")
    val email: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String
)