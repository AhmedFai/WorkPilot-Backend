package com.faizan.workpilot.dto.response

data class ProjectHeadDashboardResponse(
    val currentProjectCount: Long,
    val taskCount: Long,
    val overdueTaskCount: Long,
    val projects: List<ProjectHeadProjectResponse>
)
