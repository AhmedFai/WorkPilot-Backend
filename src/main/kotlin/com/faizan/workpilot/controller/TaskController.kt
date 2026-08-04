package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateTaskRequest
import com.faizan.workpilot.dto.request.UpdateTaskRequest
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.dto.response.TaskResponse
import com.faizan.workpilot.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @GetMapping
    fun getAllProjects(): ResponseEntity<SuccessResponse<List<TaskResponse>>> {
        val getAllTasks = taskService.getAllTasks()
        return ResponseEntity.ok(
            SuccessResponse(
                message = "All tasks are fetched successfully",
                data = getAllTasks
            )
        )
    }

    @GetMapping("/{id}")
    fun getTaskById(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<TaskResponse>> {
        val getTask = taskService.getTaskById(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Task fetched successfullly",
                data = getTask
            )
        )
    }

    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        request: UpdateTaskRequest
    ): ResponseEntity<SuccessResponse<TaskResponse>> {
        val updatedTask = taskService.updateTask(id, request)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Task updated successfully",
                data = updatedTask
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteTask(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<TaskResponse>> {
        val deletedTask = taskService.deleteTask(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Task deleted successfully",
                data = deletedTask
            )
        )
    }

}