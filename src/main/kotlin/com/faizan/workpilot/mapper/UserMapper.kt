package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.request.CreateCompanyAdminRequest
import com.faizan.workpilot.dto.request.CreateUserRequest
import com.faizan.workpilot.dto.response.CompanyAdminDetailsResponse
import com.faizan.workpilot.dto.response.CompanyAdminPreviewResponse
import com.faizan.workpilot.dto.response.CompanyAdminResponse
import com.faizan.workpilot.dto.response.CompanySummaryResponse
import com.faizan.workpilot.dto.response.UserResponse
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.entity.User
import com.faizan.workpilot.enums.Role

fun CreateUserRequest.toEntity(
    company: Company?,
    hashedPassword: String
): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = hashedPassword,
        phoneNumber = phoneNumber,
        designation = designation,
        role = role,
        company = company
    )
}

fun User.toResponse(): UserResponse{
    return UserResponse(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        designation = designation,
        role = role,
        company = company?.let {
            CompanySummaryResponse(
                id = it.id!!,
                name = it.name
            )
        },
        isActive = isActive,
        createdAt = createdAt.toString()
    )
}

fun User.toCompanyAdminPreviewResponse(): CompanyAdminPreviewResponse {
    return CompanyAdminPreviewResponse(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        active = this.isActive
    )
}

fun User.toCompanyAdminResponse(): CompanyAdminResponse {
    return CompanyAdminResponse(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        active = this.isActive,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}

fun CreateCompanyAdminRequest.toEntity(
    company: Company,
    hashedPassword: String
): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = hashedPassword,
        phoneNumber = "",
        designation = "ADMIN",
        role = Role.ADMIN,
        company = company
    )
}

fun User.toCompanyAdminDetailsResponse(): CompanyAdminDetailsResponse {
    val userCompany = this.company!!

    return CompanyAdminDetailsResponse(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        role = this.role,
        companyId = userCompany.id!!,
        companyName = userCompany.name,
        active = this.isActive,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}