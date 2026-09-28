package com.faizan.workpilot.service

import com.faizan.workpilot.dto.response.SuperAdminDashboardResponse
import com.faizan.workpilot.mapper.toSuperAdminCompanyResponse
import com.faizan.workpilot.repository.CompanyRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuperAdminDashboardService(
    private val companyRepository: CompanyRepository
) {

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional(readOnly = true)
    fun getDashboard(): SuperAdminDashboardResponse {

        val companies =
            companyRepository
                .findAll()
                .map { it.toSuperAdminCompanyResponse() }

        return SuperAdminDashboardResponse(
            companies = companies
        )
    }
}