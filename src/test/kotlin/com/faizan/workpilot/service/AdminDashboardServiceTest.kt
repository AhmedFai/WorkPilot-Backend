
package com.faizan.workpilot.service

import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.entity.Project
import com.faizan.workpilot.entity.User
import com.faizan.workpilot.enums.Role
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.repository.UserRepository
import com.faizan.workpilot.security.CurrentUserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class AdminDashboardServiceTest {

    private val currentUserService = mock<CurrentUserService>()
    private val userRepository = mock<UserRepository>()
    private val projectRepository = mock<ProjectRepository>()
    private val taskRepository = mock<TaskRepository>()

    private val adminDashboardService = AdminDashboardService(
        currentUserService,
        userRepository,
        projectRepository,
        taskRepository
    )

    @Test
    fun getDashboard_returnsCorrectCounts() {

        // GIVEN: Admin belongs to company 10

        val company = Company(
            id = 10L,
            name = "WorkPilot Company",
            email = "admin@workpilot.com"
        )

        val admin = User(
            firstName = "Faizan",
            lastName = "Ahmed",
            email = "admin@workpilot.com",
            password = "password",
            phoneNumber = "9999999999",
            designation = "Admin",
            role = Role.ADMIN,
            company = company
        )

        whenever(currentUserService.getCurrentUser())
            .thenReturn(admin)

        whenever(
            userRepository.countByCompanyIdAndRoleAndIsActiveTrue(
                10L,
                Role.EMPLOYEE
            )
        ).thenReturn(5L)

        whenever(
            projectRepository.countByCompanyIdAndIsActiveTrue(10L)
        ).thenReturn(3L)

        whenever(
            taskRepository.countByProjectCompanyIdAndIsActiveTrue(10L)
        ).thenReturn(12L)

        whenever(
            projectRepository.findTop5ByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(10L)
        ).thenReturn(emptyList())

        // WHEN: Dashboard is requested

        val result = adminDashboardService.getDashboard()

        // THEN: Correct dashboard data is returned

        assertEquals(10L, result.company.id)
        assertEquals(5L, result.employeeCount)
        assertEquals(3L, result.projectCount)
        assertEquals(12L, result.taskCount)
        assertEquals(0, result.recentProjects.size)

        verify(currentUserService).getCurrentUser()
        verify(userRepository)
            .countByCompanyIdAndRoleAndIsActiveTrue(10L, Role.EMPLOYEE)

        verify(projectRepository)
            .countByCompanyIdAndIsActiveTrue(10L)

        verify(taskRepository)
            .countByProjectCompanyIdAndIsActiveTrue(10L)

        verify(projectRepository)
            .findTop5ByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(10L)

    }
}
