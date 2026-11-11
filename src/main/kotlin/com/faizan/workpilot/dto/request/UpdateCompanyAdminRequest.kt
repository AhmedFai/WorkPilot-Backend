package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.NotBlank

data class UpdateCompanyAdminRequest(

    @field:NotBlank(message = "First name cannot be empty")
    val firstName: String,

    @field:NotBlank(message = "Last name cannot be empty")
    val lastName: String,

    @field:NotBlank(message = "Phone number cannot be empty")
    val phoneNumber: String,

)
