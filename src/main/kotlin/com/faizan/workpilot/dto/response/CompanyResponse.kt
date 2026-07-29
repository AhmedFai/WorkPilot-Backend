package com.faizan.workpilot.dto.response

data class CompanyResponse(
    val id: Long,
    val name: String,
    val email: String,
    val website: String?,
    val isActive: Boolean,
    val createdAt: String
)
