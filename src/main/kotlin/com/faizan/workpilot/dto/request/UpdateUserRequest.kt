package com.faizan.workpilot.dto.request

import com.faizan.workpilot.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    @field:NotBlank(message = "First name should not be empty")
    val firstName: String,

    @field:NotBlank(message = "Last name should not be empty")
    val lastName: String,

    @field:NotBlank(message = "Email should not be empty")
    @field:Email(message = "Invalid email")
    val email: String,

    @field:NotBlank(message = "Phone number should not be empty")
    val phoneNumber: String,

    @field:NotBlank(message = "Designation should not be empty")
    val designation: String,

    val role: Role,

    @field:Positive(message = "Invalid company id")
    val companyId: Long
)
