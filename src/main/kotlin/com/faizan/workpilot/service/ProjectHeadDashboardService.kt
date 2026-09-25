package com.faizan.workpilot.service

import com.faizan.workpilot.dto.response.ProjectHeadDashboardResponse
import com.faizan.workpilot.dto.response.ProjectHeadProjectResponse
import com.faizan.workpilot.enums.Status
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.security.CurrentUserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProjectHeadDashboardService(
    private val currentUserService: CurrentUserService,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    @PreAuthorize("hasRole('PROJECT_HEAD')")
    @Transactional(readOnly = true)
    fun getDashboard(): ProjectHeadDashboardResponse {

        val currentUser = currentUserService.getCurrentUser()
        val projectHeadId = currentUser.id!!

        val currentProjectCount =
            projectRepository.countByProjectHeadIdAndIsActiveTrue(
                projectHeadId
            )

        val taskCount =
            taskRepository.countByProjectProjectHeadIdAndProjectIsActiveTrueAndIsActiveTrue(
                projectHeadId
            )

        val overdueTaskCount =
            taskRepository
                .countByProjectProjectHeadIdAndProjectIsActiveTrueAndIsActiveTrueAndDeadlineBeforeAndStatusNotIn(
                    projectHeadId,
                    LocalDateTime.now(),
                    listOf(
                        Status.COMPLETED,
                        Status.APPROVED
                    )
                )

        val projects =
            projectRepository
                .findAllByProjectHeadIdAndIsActiveTrueOrderByCreatedAtDesc(
                    projectHeadId
                )
                .map { project ->

                    val totalTasks =
                        taskRepository.countByProjectIdAndIsActiveTrue(
                            project.id!!
                        )

                    val completedTasks =
                        taskRepository.countByProjectIdAndStatusAndIsActiveTrue(
                            project.id!!,
                            Status.COMPLETED
                        )

                    val progress =
                        if (totalTasks == 0L) {
                            0
                        } else {
                            ((completedTasks * 100) / totalTasks).toInt()
                        }

                    ProjectHeadProjectResponse(
                        id = project.id!!,
                        name = project.name,
                        progress = progress
                    )
                }

        return ProjectHeadDashboardResponse(
            currentProjectCount = currentProjectCount,
            taskCount = taskCount,
            overdueTaskCount = overdueTaskCount,
            projects = projects
        )
    }

}