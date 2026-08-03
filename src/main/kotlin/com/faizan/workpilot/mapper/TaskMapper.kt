package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.request.CreateTaskRequest
import com.faizan.workpilot.dto.response.ProjectSummaryResponse
import com.faizan.workpilot.dto.response.TaskResponse
import com.faizan.workpilot.dto.response.UserSummaryResponse
import com.faizan.workpilot.entity.Project
import com.faizan.workpilot.entity.Task
import com.faizan.workpilot.entity.User

fun CreateTaskRequest.toEntity(user: User, project: Project): Task {
    return Task(
        title = title,
        description = description,
        assignedTo = user,
        project = project,
        priority = priority,
        status = status,
        deadline = deadline
    )
}

fun Task.toResponse(): TaskResponse {
    return TaskResponse(
        id = id!!,
        title = title,
        description = description,
        assignedTo = UserSummaryResponse(
            assignedTo.id!!,
            assignedTo.firstName,
            assignedTo.lastName
        ),
        project = ProjectSummaryResponse(
            project.id!!,
            project.name
        ),
        priority = priority,
        status = status,
        deadline = deadline.toString(),
        isActive = isActive,
        createdAt = createdAt.toString()
    )
}