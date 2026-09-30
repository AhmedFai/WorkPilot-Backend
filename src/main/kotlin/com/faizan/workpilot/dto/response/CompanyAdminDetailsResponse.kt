package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.Role

data class CompanyAdminDetailsResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val companyId: Long,
    val companyName: String,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
)
