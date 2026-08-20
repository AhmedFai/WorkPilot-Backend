package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.Task
import com.faizan.workpilot.enums.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TaskRepository: JpaRepository<Task, Long> {

    fun findAllByIsActiveTrue(): List<Task>

    fun countByProjectCompanyIdAndIsActiveTrue(
        companyId: Long
    ): Long

    fun countByAssignedToIdAndIsActiveTrue(
        userId: Long
    ): Long

    fun countByAssignedToIdAndStatusAndDeadlineGreaterThanEqualAndDeadlineLessThanAndIsActiveTrue(
        userId: Long,
        status: Status,
        startOfDay: LocalDateTime,
        startOfNextDay: LocalDateTime
    ): Long

    fun findAllByAssignedToIdAndDeadlineGreaterThanEqualAndDeadlineLessThanAndIsActiveTrueOrderByDeadlineAsc(
        userId: Long,
        startOfDay: LocalDateTime,
        startOfNextDay: LocalDateTime
    ): List<Task>

}