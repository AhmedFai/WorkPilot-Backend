package com.faizan.workpilot.service

import com.faizan.workpilot.entity.Activity
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.entity.User
import com.faizan.workpilot.enums.ActivityType
import com.faizan.workpilot.repository.ActivityRepository
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val activityRepository: ActivityRepository
) {

    fun createActivity(
        company: Company,
        type: ActivityType,
        message: String,
        description: String?,
        performedBy: User?
    ): Activity {

        val activity = Activity(
            company = company,
            type = type,
            message = message,
            description = description,
            performedBy = performedBy
        )

        return activityRepository.save(activity)
    }
}