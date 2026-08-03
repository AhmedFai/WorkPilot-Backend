package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateTaskRequest
import com.faizan.workpilot.dto.response.TaskResponse
import com.faizan.workpilot.exception.ProjectNotFoundException
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    fun createTask(
        request: CreateTaskRequest
    ): TaskResponse {

        val assignedUser = userRepository.findById(request.assignToId)
            .orElseThrow {
                UserNotFoundException("Assigned user with id ${request.assignToId} not found")
            }
        val project = projectRepository.findById(request.projectId)
            .orElseThrow {
                ProjectNotFoundException("Project with id ${request.projectId} not found")
            }
        val task = request.toEntity(assignedUser, project)
        val savedTask = taskRepository.save(task)
        return savedTask.toResponse()
    }

}