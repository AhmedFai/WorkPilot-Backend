package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.ProjectStatus

data class RecentProjectResponse(
    val id: Long,
    val name: String,
    val status: ProjectStatus,
    val logoUrl: String?
)