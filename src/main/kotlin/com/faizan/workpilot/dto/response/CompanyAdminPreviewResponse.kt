package com.faizan.workpilot.dto.response

data class CompanyAdminPreviewResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val active: Boolean
)