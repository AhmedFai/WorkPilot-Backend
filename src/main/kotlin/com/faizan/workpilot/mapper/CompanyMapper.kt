package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.request.CreateCompanyRequest
import com.faizan.workpilot.dto.response.CompanyResponse
import com.faizan.workpilot.entity.Company

fun CreateCompanyRequest.toEntity(): Company {
    return Company(
        name = name,
        email = email,
        website = website
    )
}

fun Company.toResponse(): CompanyResponse {
    return CompanyResponse(
        id = id!!,
        name = name,
        email = email,
        website = website,
        isActive = isActive,
        createdAt = createdAt.toString()
    )
}