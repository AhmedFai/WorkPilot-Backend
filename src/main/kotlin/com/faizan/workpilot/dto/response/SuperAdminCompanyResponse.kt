package com.faizan.workpilot.dto.response

data class SuperAdminCompanyResponse(
    val id: Long,
    val name: String,
    val logoUrl: String?,
    val isActive: Boolean
)
