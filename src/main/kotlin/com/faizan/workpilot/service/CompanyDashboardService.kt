package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateCompanyAdminRequest
import com.faizan.workpilot.dto.request.UpdateAdminStatusRequest
import com.faizan.workpilot.dto.response.CompanyActivityListResponse
import com.faizan.workpilot.dto.response.CompanyAdminDetailsResponse
import com.faizan.workpilot.dto.response.CompanyOverviewResponse
import com.faizan.workpilot.dto.response.CompanyTaskSummaryResponse
import com.faizan.workpilot.mapper.toCompanyAdminPreviewResponse
import com.faizan.workpilot.mapper.toCompanyActivityPreviewResponse
import com.faizan.workpilot.mapper.toCompanyOverviewResponse
import com.faizan.workpilot.enums.Role
import com.faizan.workpilot.enums.Status
import com.faizan.workpilot.exception.CompanyNotFoundException
import com.faizan.workpilot.repository.ActivityRepository
import com.faizan.workpilot.repository.CompanyRepository
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.faizan.workpilot.dto.response.CompanyAdminListResponse
import com.faizan.workpilot.dto.response.CompanyAdminResponse
import com.faizan.workpilot.enums.ActivityType
import com.faizan.workpilot.exception.UserAlreadyExistsException
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.mapper.toCompanyAdminDetailsResponse
import com.faizan.workpilot.mapper.toCompanyAdminResponse
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.security.CurrentUserService
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class CompanyDashboardService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
    private val activityRepository: ActivityRepository,
    private val activityService: ActivityService,
    private val currentUserService: CurrentUserService,
    private val passwordEncoder: PasswordEncoder
) {

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional(readOnly = true)
    fun getDashboard(companyId: Long): CompanyOverviewResponse {

        val company = companyRepository.findById(companyId)
            .orElseThrow {
                CompanyNotFoundException(
                    "Company with id $companyId not found"
                )
            }

        val totalUsers =
            userRepository.countByCompanyIdAndIsActiveTrue(companyId)

        val activeProjects =
            projectRepository.countByCompanyIdAndIsActiveTrue(companyId)

        val totalTasks =
            taskRepository.countByProjectCompanyIdAndIsActiveTrue(companyId)

        val pendingTasks =
            taskRepository.countByProjectCompanyIdAndStatusAndIsActiveTrue(
                companyId,
                Status.PENDING
            )

        val inProgressTasks =
            taskRepository.countByProjectCompanyIdAndStatusAndIsActiveTrue(
                companyId,
                Status.IN_PROGRESS
            )

        val completedTasks =
            taskRepository.countByProjectCompanyIdAndStatusAndIsActiveTrue(
                companyId,
                Status.COMPLETED
            )

        val overdueTasks =
            taskRepository
                .countByProjectCompanyIdAndIsActiveTrueAndDeadlineBeforeAndStatusNotIn(
                    companyId,
                    LocalDateTime.now(),
                    listOf(Status.COMPLETED, Status.APPROVED)
                )

        val adminPreview =
            userRepository
                .findTop5ByCompanyIdAndRoleAndIsActiveTrueOrderByCreatedAtDesc(
                    companyId,
                    Role.ADMIN
                )
                .map { it.toCompanyAdminPreviewResponse() }

        val recentActivities =
            activityRepository
                .findTop5ByCompanyIdOrderByCreatedAtDesc(companyId)
                .map { it.toCompanyActivityPreviewResponse() }

        return CompanyOverviewResponse(
            company = company.toCompanyOverviewResponse(),
            totalUsers = totalUsers,
            activeProjects = activeProjects,
            totalTasks = totalTasks,
            taskSummary = CompanyTaskSummaryResponse(
                pending = pendingTasks,
                inProgress = inProgressTasks,
                completed = completedTasks,
                overdue = overdueTasks
            ),
            recentActivities = recentActivities,
            adminPreview = adminPreview
        )
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional(readOnly = true)
    fun getCompanyAdmins(
        companyId: Long,
        page: Int,
        size: Int,
        search: String?
    ): CompanyAdminListResponse {

        companyRepository.findById(companyId)
            .orElseThrow {
                CompanyNotFoundException(
                    "Company with id $companyId not found"
                )
            }

        val pageable = PageRequest.of(
            page,
            size
        )

        val admins = if (search.isNullOrBlank()) {
            userRepository.findCompanyAdmins(
                companyId = companyId,
                role = Role.ADMIN,
                pageable = pageable
            )
        } else {
            userRepository.searchCompanyAdmins(
                companyId = companyId,
                role = Role.ADMIN,
                search = search.trim(),
                pageable = pageable
            )
        }

        return CompanyAdminListResponse(
            content = admins.content.map {
                it.toCompanyAdminResponse()
            },
            page = admins.number,
            size = admins.size,
            totalElements = admins.totalElements,
            totalPages = admins.totalPages
        )
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional
    fun createCompanyAdmin(
        companyId: Long,
        request: CreateCompanyAdminRequest
    ): CompanyAdminResponse {

        val company = companyRepository.findById(companyId)
            .orElseThrow {
                CompanyNotFoundException("Company with id $companyId not found")
            }

        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("Email already registered")
        }

        val hashedPassword = passwordEncoder.encode(request.password)

        val admin = request.toEntity(
            company = company,
            hashedPassword = hashedPassword
        )

        val savedAdmin = userRepository.save(admin)

        activityService.createActivity(
            company = company,
            type = ActivityType.USER_CREATED,
            message = "New admin created",
            description = "${savedAdmin.firstName} ${savedAdmin.lastName} was added as a company admin.",
            performedBy = currentUserService.getCurrentUser()
        )

        return savedAdmin.toCompanyAdminResponse()
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional(readOnly = true)
    fun getCompanyAdminDetails(
        companyId: Long,
        adminId: Long
    ): CompanyAdminDetailsResponse {

        val admin = userRepository.findCompanyAdminById(
            adminId = adminId,
            companyId = companyId,
            role = Role.ADMIN
        ) ?: throw UserNotFoundException(
            "Admin with id $adminId not found in company $companyId"
        )

        return admin.toCompanyAdminDetailsResponse()
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional
    fun updateCompanyAdminStatus(
        companyId: Long,
        adminId: Long,
        request: UpdateAdminStatusRequest
    ): CompanyAdminDetailsResponse {

        val admin = userRepository.findCompanyAdminById(
            adminId = adminId,
            companyId = companyId,
            role = Role.ADMIN
        ) ?: throw UserNotFoundException(
            "Admin with id $adminId not found in company $companyId"
        )

        admin.isActive = request.active
        admin.updatedAt = LocalDateTime.now()

        val updatedAdmin = userRepository.save(admin)

        return updatedAdmin.toCompanyAdminDetailsResponse()
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional(readOnly = true)
    fun getCompanyActivities(
        companyId: Long,
        page: Int,
        size: Int
    ): CompanyActivityListResponse {

        companyRepository.findById(companyId)
            .orElseThrow {
                CompanyNotFoundException(
                    "Company with id $companyId not found"
                )
            }

        val pageable = PageRequest.of(page, size)

        val activities = activityRepository
            .findAllByCompanyIdOrderByCreatedAtDesc(
                companyId = companyId,
                pageable = pageable
            )

        return CompanyActivityListResponse(
            content = activities.content.map {
                it.toCompanyActivityPreviewResponse()
            },
            page = activities.number,
            size = activities.size,
            totalElements = activities.totalElements,
            totalPages = activities.totalPages
        )
    }


}