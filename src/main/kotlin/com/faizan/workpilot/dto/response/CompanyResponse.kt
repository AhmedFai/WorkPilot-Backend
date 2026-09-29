package com.faizan.workpilot.dto.response

data class CompanyResponse(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String?,
    val website: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val city: String?,
    val state: String?,
    val postalCode: String?,
    val country: String?,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)