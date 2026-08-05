package com.faizan.workpilot.dto.response

data class LoginResponse(
    val token: String,
    val user: LoggedInUserResponse
)
