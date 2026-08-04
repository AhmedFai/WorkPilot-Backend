package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UpdateCompanyRequest(
    @field:NotBlank(message = "Name cannot be empty")
    val name: String,

    @field:NotBlank(message = "Email cannot be empty")
    @field:Email(message = "Invalid email")
    val email: String,

    val website: String? = null
)
