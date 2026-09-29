package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.request.CreateCompanyRequest
import com.faizan.workpilot.dto.response.CompanyDashboardResponse
import com.faizan.workpilot.dto.response.CompanyResponse
import com.faizan.workpilot.dto.response.SuperAdminCompanyResponse
import com.faizan.workpilot.entity.Company

fun CreateCompanyRequest.toEntity(): Company {
    return Company(
        name = name,
        email = email,
        phone = phone,
        website = website,
        addressLine1 = addressLine1,
        addressLine2 = addressLine2,
        city = city,
        state = state,
        postalCode = postalCode,
        country = country
    )
}

fun Company.toResponse(): CompanyResponse {
    return CompanyResponse(
        id = id!!,
        name = name,
        email = email,
        phone = phone,
        website = website,
        addressLine1 = addressLine1,
        addressLine2 = addressLine2,
        city = city,
        state = state,
        postalCode = postalCode,
        country = country,
        isActive = isActive,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

fun Company.toDashboardResponse(): CompanyDashboardResponse {
    return CompanyDashboardResponse(
        id = id!!,
        name = name,
        email = email,
        logoUrl = null
    )
}

fun Company.toSuperAdminCompanyResponse(): SuperAdminCompanyResponse {
    return SuperAdminCompanyResponse(
        id = this.id!!,
        name = this.name,
        logoUrl = null,
        isActive = this.isActive
    )
}