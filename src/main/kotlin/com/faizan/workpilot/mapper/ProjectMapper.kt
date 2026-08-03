package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.request.CreateProjectRequest
import com.faizan.workpilot.dto.response.CompanySummaryResponse
import com.faizan.workpilot.dto.response.ProjectResponse
import com.faizan.workpilot.dto.response.UserSummaryResponse
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.entity.Project
import com.faizan.workpilot.entity.User

fun CreateProjectRequest.toEntity(company: Company, projectHead: User): Project {
    return Project(
        name = name,
        description = description,
        company = company,
        projectHead = projectHead
    )
}

fun Project.toResponse(): ProjectResponse {
    return ProjectResponse(
        id = id!!,
        name = name,
        description = description,
        company = CompanySummaryResponse(
            company.id!!,
            company.name
        ),
        projectHead = UserSummaryResponse(
            projectHead.id!!,
            projectHead.firstName,
            projectHead.lastName
        ),
        isActive = isActive,
        createdAt = createdAt.toString()
    )
}