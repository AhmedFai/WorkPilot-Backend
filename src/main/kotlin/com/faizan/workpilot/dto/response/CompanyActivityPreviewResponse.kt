package com.faizan.workpilot.dto.response

data class CompanyActivityPreviewResponse(
    val id: Long,
    val type: String,
    val message: String,
    val description: String?,
    val performedBy: String,
    val createdAt: String
)