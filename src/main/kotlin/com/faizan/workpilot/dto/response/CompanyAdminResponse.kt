package com.faizan.workpilot.dto.response

data class CompanyAdminResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
)