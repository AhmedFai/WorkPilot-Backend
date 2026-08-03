package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateProjectRequest
import com.faizan.workpilot.dto.response.ProjectResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.ProjectService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
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

}