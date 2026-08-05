package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.LoginRequest
import com.faizan.workpilot.dto.response.LoggedInUserResponse
import com.faizan.workpilot.dto.response.LoginResponse
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
            userRepository.findByEmail(request.email)
                ?: throw InvalidCredentialsException("Invalid email or password")

        if (!user.isActive) {
            throw AccountDisabledException(
                "Your account has been deactivated. Please contact your administrator."
            )
        }

        val isPasswordMatched = passwordEncoder.matches(
            request.password,
            user.password
        )

        if (!isPasswordMatched) {
            throw InvalidCredentialsException("Invalid email or password")
        }
        val token = jwtService.generateToken(user)
        val loggedInUser = LoggedInUserResponse(
            id = user.id!!,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            role = user.role
        )
       return LoginResponse(
            token = token,
           user = loggedInUser
        )
    }

}