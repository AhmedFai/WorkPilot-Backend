package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.Activity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ActivityRepository : JpaRepository<Activity, Long> {

    fun findTop5ByCompanyIdOrderByCreatedAtDesc(
        companyId: Long
    ): List<Activity>

    fun findAllByCompanyIdOrderByCreatedAtDesc(
        companyId: Long,
        pageable: Pageable
    ): Page<Activity>
}