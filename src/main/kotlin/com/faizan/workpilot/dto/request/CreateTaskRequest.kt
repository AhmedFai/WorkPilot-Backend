package com.faizan.workpilot.dto.request

import com.faizan.workpilot.enums.Priority
import com.faizan.workpilot.enums.Status
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.time.LocalDateTime

data class CreateTaskRequest(

    @field:NotBlank(message = "Please enter title of task")
    val title: String,

    val description: String?,

    @field:Positive(message = "Invalid user id")
    val assignToId: Long,

    @field:Positive(message = "Invalid project id")
    val projectId: Long,

    val status: Status,
    val priority: Priority,
    val deadline: LocalDateTime
)
