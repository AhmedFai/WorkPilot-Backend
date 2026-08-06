package com.faizan.workpilot.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    val refreshToken: String
)
