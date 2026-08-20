package com.faizan.workpilot.dto.response

data class EmployeeDashboardResponse(
    val myTaskCount: Long,
    val completedTaskCount: Long,
    val pendingTaskCount: Long,
    val todayTasks: List<EmployeeTodayTaskResponse>
)
