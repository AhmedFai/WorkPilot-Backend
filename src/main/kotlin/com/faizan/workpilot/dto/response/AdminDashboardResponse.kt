package com.faizan.workpilot.dto.response

data class AdminDashboardResponse(
    val company: CompanyDashboardResponse,
    val employeeCount: Long,
    val projectCount: Long,
    val taskCount: Long,
    val recentProjects: List<RecentProjectResponse>
)
