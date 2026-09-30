package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.response.CompanyActivityPreviewResponse
import com.faizan.workpilot.entity.Activity

fun Activity.toCompanyActivityPreviewResponse(): CompanyActivityPreviewResponse {
    return CompanyActivityPreviewResponse(
        id = this.id!!,
        type = this.type.name,
        message = this.message,
        description = this.description,
        performedBy = this.performedBy?.let {
            "${it.firstName} ${it.lastName}"
        } ?: "System",
        createdAt = this.createdAt.toString()
    )
}