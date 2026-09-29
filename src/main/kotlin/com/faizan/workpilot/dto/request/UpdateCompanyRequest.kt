package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UpdateCompanyRequest(

    @field:NotBlank(message = "Name cannot be empty")
    val name: String,

    @field:NotBlank(message = "Email cannot be empty")
    @field:Email(message = "Invalid email")
    val email: String,

    val phone: String? = null,

    val website: String? = null,

    val addressLine1: String? = null,

    val addressLine2: String? = null,

    val city: String? = null,

    val state: String? = null,

    val postalCode: String? = null,

    val country: String? = null
)