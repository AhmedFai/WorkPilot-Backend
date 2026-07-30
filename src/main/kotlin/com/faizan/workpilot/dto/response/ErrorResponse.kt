package com.faizan.workpilot.dto.response

data class ErrorResponse(
    val message: String,
    val errors: Map<String, String>? = null
)
