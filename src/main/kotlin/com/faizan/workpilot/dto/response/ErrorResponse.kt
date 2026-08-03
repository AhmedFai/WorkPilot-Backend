package com.faizan.workpilot.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val message: String,
    val errors: Map<String, String>? = null
)
