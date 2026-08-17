package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.response.AdminDashboardResponse
import com.faizan.workpilot.service.AdminDashboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminDashboardController(
    private val adminDashboardService: AdminDashboardService
) {

    @GetMapping("/dashboard")
    fun getDashboard(): ResponseEntity<AdminDashboardResponse> {
        return ResponseEntity.ok(
            adminDashboardService.getDashboard()
        )
    }
}