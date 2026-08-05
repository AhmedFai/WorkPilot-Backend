package com.faizan.workpilot.mapper

import com.faizan.workpilot.dto.request.CreateUserRequest
import com.faizan.workpilot.dto.response.CompanySummaryResponse
import com.faizan.workpilot.dto.response.UserResponse
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.entity.User

fun CreateUserRequest.toEntity(
    company: Company,
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
        company = CompanySummaryResponse(
            id = company.id!!,
            name = company.name
        ),
        isActive = isActive,
        createdAt = createdAt.toString()
    )
}