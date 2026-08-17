package com.faizan.workpilot.service

import com.faizan.workpilot.dto.response.AdminDashboardResponse
import com.faizan.workpilot.dto.response.CompanyDashboardResponse
import com.faizan.workpilot.dto.response.RecentProjectResponse
import com.faizan.workpilot.enums.Role
import com.faizan.workpilot.mapper.toDashboardResponse
import com.faizan.workpilot.mapper.toRecentProjectResponse
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.repository.UserRepository
import com.faizan.workpilot.security.CurrentUserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminDashboardService(
    private val currentUserService: CurrentUserService,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    fun getDashboard(): AdminDashboardResponse {

        val currentUser = currentUserService.getCurrentUser()
        val company = currentUser.company
        val companyId = company.id!!

        val employeeCount =
            userRepository.countByCompanyIdAndRoleAndIsActiveTrue(
                companyId,
                Role.EMPLOYEE
            )

        val projectCount =
            projectRepository.countByCompanyIdAndIsActiveTrue(
                companyId
            )

        val taskCount =
            taskRepository.countByProjectCompanyIdAndIsActiveTrue(
                companyId
            )

        val recentProjects =
            projectRepository
                .findTop5ByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(
                    companyId
                )
                .map { it.toRecentProjectResponse() }

        return AdminDashboardResponse(
            company = company.toDashboardResponse(),
            employeeCount = employeeCount,
            projectCount = projectCount,
            taskCount = taskCount,
            recentProjects = recentProjects
        )
    }
}