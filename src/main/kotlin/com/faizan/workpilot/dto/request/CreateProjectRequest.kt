package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class CreateProjectRequest(

    @field:NotBlank(message = "Please enter project name")
    val name: String,

    val description: String? = null,

    @field:Positive(message = "Invalid company id")
    val companyId: Long,

    @field:Positive(message = "Invalid project head id")
    val projectHeadId: Long
)
