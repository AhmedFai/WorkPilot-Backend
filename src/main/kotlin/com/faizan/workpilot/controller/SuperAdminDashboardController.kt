package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.dto.response.SuperAdminDashboardResponse
import com.faizan.workpilot.service.SuperAdminDashboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/super-admin")
class SuperAdminDashboardController(
    private val superAdminDashboardService: SuperAdminDashboardService
) {

    @GetMapping("/dashboard")
    fun getDashboard(): ResponseEntity<SuccessResponse<SuperAdminDashboardResponse>> {
        val dashboard = superAdminDashboardService.getDashboard()
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Super Admin dashboard fetched successfully",
                data = dashboard
            )

        )
    }
}