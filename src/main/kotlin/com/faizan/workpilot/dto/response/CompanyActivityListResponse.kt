package com.faizan.workpilot.dto.response

data class CompanyActivityListResponse(
    val content: List<CompanyActivityPreviewResponse>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)
