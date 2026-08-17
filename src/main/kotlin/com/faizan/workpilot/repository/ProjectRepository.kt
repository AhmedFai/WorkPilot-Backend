package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository: JpaRepository<Project, Long> {

    fun findAllByIsActiveTrue(): List<Project>

    fun countByCompanyIdAndIsActiveTrue(
        companyId: Long
    ): Long

    fun findTop5ByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(
        companyId: Long
    ): List<Project>

}