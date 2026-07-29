package com.faizan.workpilot.dto.response

data class SuccessResponse<T>(
    val message: String,
    val data: T
)