package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.response.EmployeeDashboardResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.EmployeeDashboardService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/employee")
class EmployeeDashboardController(
    private val employeeDashboardService: EmployeeDashboardService
) {

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/dashboard")
    fun getDashboard():
            ResponseEntity<SuccessResponse<EmployeeDashboardResponse>> {

        val dashboard =
            employeeDashboardService.getDashboard()

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Employee dashboard fetched successfully",
                data = dashboard
            )
        )
    }
}