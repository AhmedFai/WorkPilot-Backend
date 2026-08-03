package com.faizan.workpilot.dto.response

import com.faizan.workpilot.enums.Role

data class UserSummaryResponse(
    val id: Long,
    val firstName: String,
    val lastName: String
)
