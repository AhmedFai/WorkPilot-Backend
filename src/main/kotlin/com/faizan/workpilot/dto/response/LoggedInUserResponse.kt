package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.Role

data class LoggedInUserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role
)
