package com.faizan.workpilot.dto.response

data class ProjectResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val company: CompanySummaryResponse,
    val projectHead: UserSummaryResponse,
    val isActive: Boolean,
    val createdAt: String
)
