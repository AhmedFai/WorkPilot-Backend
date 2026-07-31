package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.Role


data class UserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val designation: String,
    val role: Role,
    val company: CompanySummaryResponse,
    val isActive: Boolean,
    val createdAt: String
)
