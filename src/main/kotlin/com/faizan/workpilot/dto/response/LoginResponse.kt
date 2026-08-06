package com.faizan.workpilot.dto.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: LoggedInUserResponse
)
