package com.faizan.workpilot.service

import com.faizan.workpilot.dto.response.EmployeeDashboardResponse
import com.faizan.workpilot.enums.Status
import com.faizan.workpilot.mapper.toEmployeeTodayTaskResponse
import com.faizan.workpilot.repository.TaskRepository
import com.faizan.workpilot.security.CurrentUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class EmployeeDashboardService(
    private val currentUserService: CurrentUserService,
    private val taskRepository: TaskRepository
) {

    @Transactional(readOnly = true)
    fun getDashboard(): EmployeeDashboardResponse {

        val currentUser =
            currentUserService.getCurrentUser()

        val userId =
            requireNotNull(currentUser.id)

        val today =
            LocalDate.now()

        val startOfDay =
            today.atStartOfDay()

        val startOfNextDay =
            today
                .plusDays(1)
                .atStartOfDay()

        val myTaskCount =
            taskRepository
                .countByAssignedToIdAndIsActiveTrue(
                    userId
                )

        val completedTaskCount =
            countTasksByStatuses(
                userId = userId,
                statuses = listOf(
                    Status.COMPLETED,
                    Status.APPROVED
                ),
                startOfDay = startOfDay,
                startOfNextDay = startOfNextDay
            )

        val pendingTaskCount =
            countTasksByStatuses(
                userId = userId,
                statuses = listOf(
                    Status.PENDING,
                    Status.IN_PROGRESS,
                    Status.REOPEN
                ),
                startOfDay = startOfDay,
                startOfNextDay = startOfNextDay
            )

        val todayTasks =
            taskRepository
                .findAllByAssignedToIdAndDeadlineGreaterThanEqualAndDeadlineLessThanAndIsActiveTrueOrderByDeadlineAsc(
                    userId = userId,
                    startOfDay = startOfDay,
                    startOfNextDay = startOfNextDay
                )
                .map {
                    it.toEmployeeTodayTaskResponse()
                }

        return EmployeeDashboardResponse(
            myTaskCount = myTaskCount,
            completedTaskCount = completedTaskCount,
            pendingTaskCount = pendingTaskCount,
            todayTasks = todayTasks
        )
    }

    private fun countTasksByStatuses(
        userId: Long,
        statuses: List<Status>,
        startOfDay: java.time.LocalDateTime,
        startOfNextDay: java.time.LocalDateTime
    ): Long {

        return statuses.sumOf { status ->

            taskRepository
                .countByAssignedToIdAndStatusAndDeadlineGreaterThanEqualAndDeadlineLessThanAndIsActiveTrue(
                    userId = userId,
                    status = status,
                    startOfDay = startOfDay,
                    startOfNextDay = startOfNextDay
                )
        }
    }
}