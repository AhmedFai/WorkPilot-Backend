package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.response.ProjectHeadDashboardResponse
import com.faizan.workpilot.service.ProjectHeadDashboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/project-head")
class ProjectHeadDashboardController(
    private val projectHeadDashboardService: ProjectHeadDashboardService
) {

    @GetMapping("/dashboard")
    fun getDashboard(): ResponseEntity<ProjectHeadDashboardResponse> {
        return ResponseEntity.ok(
            projectHeadDashboardService.getDashboard()
        )
    }
}