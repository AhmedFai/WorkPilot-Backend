package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.Priority
import com.faizan.workpilot.enums.Status

data class EmployeeTodayTaskResponse(
    val id: Long,
    val title: String,
    val project: ProjectSummaryResponse,
    val priority: Priority,
    val status: Status,
    val deadline: String
)
