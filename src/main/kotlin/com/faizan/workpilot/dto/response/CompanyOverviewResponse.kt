package com.faizan.workpilot.dto.response

data class CompanyOverviewResponse(
    val company: CompanyOverviewCompanyResponse,
    val totalUsers: Long,
    val activeProjects: Long,
    val totalTasks: Long,
    val taskSummary: CompanyTaskSummaryResponse,
    val recentActivities: List<CompanyActivityPreviewResponse>,
    val adminPreview: List<CompanyAdminPreviewResponse>
)