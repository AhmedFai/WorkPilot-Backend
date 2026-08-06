package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateTaskRequest
import com.faizan.workpilot.dto.request.UpdateTaskRequest
import com.faizan.workpilot.dto.response.TaskResponse
import com.faizan.workpilot.exception.ProjectNotFoundException
import com.faizan.workpilot.exception.TaskNotFoundException
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
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

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun getAllTasks(): List<TaskResponse> {
        val getAllTasks = taskRepository.findAllByIsActiveTrue()
        return getAllTasks.map { it.toResponse() }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun getTaskById(id: Long): TaskResponse {
        val task = taskRepository.findById(id).orElseThrow {
            TaskNotFoundException("Task with id $id not found")
        }
        return task.toResponse()
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun updateTask(
        id: Long,
        request: UpdateTaskRequest
    ): TaskResponse {
        val task = taskRepository.findById(id).orElseThrow {
            TaskNotFoundException("Task with id $id not found")
        }
        val assignedUser = userRepository.findById(request.assignToId)
            .orElseThrow {
                UserNotFoundException("Assigned user with id ${request.assignToId} not found")
            }
        val project = projectRepository.findById(request.projectId)
            .orElseThrow {
                ProjectNotFoundException("Project with id ${request.projectId} not found")
            }
        task.title = request.title
        task.description = request.description
        task.assignedTo = assignedUser
        task.project = project
        task.status = request.status
        task.priority = request.priority
        task.deadline = request.deadline
        val updatedTask = taskRepository.save(task)
        return updatedTask.toResponse()
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun deleteTask(
        id: Long
    ): TaskResponse {
        val task = taskRepository.findById(id).orElseThrow {
            TaskNotFoundException("Task with id $id not found")
        }
        task.isActive = false
        val updatedTask = taskRepository.save(task)
        return updatedTask.toResponse()
    }

}