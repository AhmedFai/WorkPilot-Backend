package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateProjectRequest
import com.faizan.workpilot.dto.request.UpdateProjectRequest
import com.faizan.workpilot.dto.response.ProjectResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.ProjectService
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
@RequestMapping("/api/v1/projects")
class ProjectController(
    private val projectService: ProjectService
) {

    @PostMapping
    fun createProject(
        @Valid
        @RequestBody request: CreateProjectRequest
    ): ResponseEntity<SuccessResponse<ProjectResponse>> {

        val savedProject = projectService.createProject(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            SuccessResponse(
                message = "Project created successfully",
                data = savedProject
            )
        )
    }

    @GetMapping
    fun getAllProjects(): ResponseEntity<SuccessResponse<List<ProjectResponse>>> {
        val allProjects = projectService.getAllProjects()
        return ResponseEntity.ok(
            SuccessResponse(
                message = "All projects are fetched successfully",
                data = allProjects
            )
        )
    }

    @GetMapping("/{id}")
    fun getProjectById(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<ProjectResponse>> {
        val project = projectService.getProjectById(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Project fetched successfully",
                data = project
            )
        )
    }

    @PutMapping("/{id}")
    fun updateProject(
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        request: UpdateProjectRequest
    ): ResponseEntity<SuccessResponse<ProjectResponse>> {
        val updatedProject = projectService.updateProject(id, request)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Project updated successfully",
                data = updatedProject
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteProject(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<ProjectResponse>> {
        val deletedProject = projectService.deleteProject(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Project deleted successfully",
                data = deletedProject
            )
        )
    }

}