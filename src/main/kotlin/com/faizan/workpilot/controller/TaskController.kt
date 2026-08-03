package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateTaskRequest
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.dto.response.TaskResponse
import com.faizan.workpilot.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @PostMapping
    fun createTask(
        @Valid
        @RequestBody request: CreateTaskRequest
    ): ResponseEntity<SuccessResponse<TaskResponse>> {
        val savedTask = taskService.createTask(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            SuccessResponse(
                message = "Task created successfully",
                data = savedTask
            )
        )
    }

}