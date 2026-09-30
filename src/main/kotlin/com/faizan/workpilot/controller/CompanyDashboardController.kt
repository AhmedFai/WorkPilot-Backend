package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateCompanyAdminRequest
import com.faizan.workpilot.dto.request.UpdateAdminStatusRequest
import com.faizan.workpilot.dto.response.CompanyActivityListResponse
import com.faizan.workpilot.dto.response.CompanyAdminDetailsResponse
import com.faizan.workpilot.dto.response.CompanyAdminListResponse
import com.faizan.workpilot.dto.response.CompanyAdminResponse
import com.faizan.workpilot.dto.response.CompanyOverviewResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.CompanyDashboardService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/companies")
class CompanyDashboardController(
    private val companyDashboardService: CompanyDashboardService
) {

    @GetMapping("/{companyId}/dashboard")
    fun getDashboard(
        @PathVariable companyId: Long
    ): ResponseEntity<SuccessResponse<CompanyOverviewResponse>> {

        val dashboard =
            companyDashboardService.getDashboard(companyId)

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Company dashboard fetched successfully",
                data = dashboard
            )
        )
    }


    @GetMapping("/{companyId}/admins")
    fun getCompanyAdmins(
        @PathVariable companyId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) search: String?
    ): ResponseEntity<SuccessResponse<CompanyAdminListResponse>> {

        val admins = companyDashboardService.getCompanyAdmins(
            companyId = companyId,
            page = page,
            size = size,
            search = search
        )

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Company admins fetched successfully",
                data = admins
            )
        )
    }

    @PostMapping("/{companyId}/admins")
    fun createCompanyAdmin(
        @PathVariable companyId: Long,
        @Valid @RequestBody request: CreateCompanyAdminRequest
    ): ResponseEntity<SuccessResponse<CompanyAdminResponse>> {

        val admin = companyDashboardService.createCompanyAdmin(
            companyId = companyId,
            request = request
        )

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Company admin created successfully",
                data = admin
            )
        )
    }

    @GetMapping("/{companyId}/admins/{adminId}")
    fun getCompanyAdminDetails(
        @PathVariable companyId: Long,
        @PathVariable adminId: Long
    ): ResponseEntity<SuccessResponse<CompanyAdminDetailsResponse>> {

        val admin = companyDashboardService.getCompanyAdminDetails(
            companyId = companyId,
            adminId = adminId
        )

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Admin details fetched successfully",
                data = admin
            )
        )
    }

    @PatchMapping("/{companyId}/admins/{adminId}/status")
    fun updateCompanyAdminStatus(
        @PathVariable companyId: Long,
        @PathVariable adminId: Long,
        @Valid @RequestBody request: UpdateAdminStatusRequest
    ): ResponseEntity<SuccessResponse<CompanyAdminDetailsResponse>> {

        val admin = companyDashboardService.updateCompanyAdminStatus(
            companyId = companyId,
            adminId = adminId,
            request = request
        )

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Admin status updated successfully",
                data = admin
            )
        )
    }


    @GetMapping("/{companyId}/activities")
    fun getCompanyActivities(
        @PathVariable companyId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<SuccessResponse<CompanyActivityListResponse>> {

        val activities = companyDashboardService.getCompanyActivities(
            companyId = companyId,
            page = page,
            size = size
        )

        return ResponseEntity.ok(
            SuccessResponse(
                message = "Company activities fetched successfully",
                data = activities
            )
        )
    }

}