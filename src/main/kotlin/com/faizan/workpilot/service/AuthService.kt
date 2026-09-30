package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.LoginRequest
import com.faizan.workpilot.dto.request.RefreshTokenRequest
import com.faizan.workpilot.dto.response.LoggedInUserResponse
import com.faizan.workpilot.dto.response.LoginResponse
import com.faizan.workpilot.dto.response.RefreshTokenResponse
import com.faizan.workpilot.entity.User
import com.faizan.workpilot.enums.Role
import com.faizan.workpilot.exception.AccountDisabledException
import com.faizan.workpilot.exception.InvalidCredentialsException
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.UserRepository
import com.faizan.workpilot.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    fun login(
        request: LoginRequest
    ): LoginResponse {
        val user =
            userRepository.findWithCompanyByEmail(request.email)
                ?: throw InvalidCredentialsException("Invalid email or password")

        validateUserAccess(user)

        val isPasswordMatched = passwordEncoder.matches(
            request.password,
            user.password
        )

        if (!isPasswordMatched) {
            throw InvalidCredentialsException("Invalid email or password")
        }
        val loggedInUser = LoggedInUserResponse(
            id = user.id!!,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            role = user.role
        )
        val accessToken =
            jwtService.generateAccessToken(user)

        val refreshToken =
            jwtService.generateRefreshToken(user)

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = loggedInUser
        )
    }

    fun refreshToken(
        request: RefreshTokenRequest
    ): RefreshTokenResponse {
        val email = jwtService.extractRefreshEmail(
            request.refreshToken
        )
        val user =
            userRepository.findWithCompanyByEmail(email)
                ?: throw InvalidCredentialsException("Invalid refresh token")

        if (!jwtService.validateRefreshToken(request.refreshToken, user)) {
            throw InvalidCredentialsException(
                "Invalid or expired refresh token"
            )
        }

        validateUserAccess(user)

        val accessToken = jwtService.generateAccessToken(user)
        return RefreshTokenResponse(
            accessToken = accessToken
        )
    }

    fun logout() {
        // V1:
        // Nothing to do.
        // Android will remove access and refresh tokens locally.
    }


    private fun validateUserAccess(user: User) {

        if (!user.isActive) {
            throw AccountDisabledException(
                "Your account has been deactivated. Please contact your administrator."
            )
        }

        if (
            user.role != Role.SUPER_ADMIN &&
            user.company?.isActive != true
        ) {
            throw AccountDisabledException(
                "Your company has been deactivated. Please contact your administrator."
            )
        }
    }

}