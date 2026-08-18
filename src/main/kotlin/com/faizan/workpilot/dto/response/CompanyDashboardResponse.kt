package com.faizan.workpilot.dto.response

data class CompanyDashboardResponse(
    val id: Long,
    val name: String,
    val email: String,
    val logoUrl: String?
)
