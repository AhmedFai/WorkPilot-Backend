package com.faizan.workpilot.dto.response

data class CompanyTaskSummaryResponse(
    val pending: Long,
    val inProgress: Long,
    val completed: Long,
    val overdue: Long
)