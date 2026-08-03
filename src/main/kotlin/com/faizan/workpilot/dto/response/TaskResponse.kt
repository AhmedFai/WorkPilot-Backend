package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.Priority
import com.faizan.workpilot.enums.Status

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val assignedTo: UserSummaryResponse,
    val project: ProjectSummaryResponse,
    val priority: Priority,
    val status: Status,
    val deadline: String,
    val isActive: Boolean,
    val createdAt: String
)
