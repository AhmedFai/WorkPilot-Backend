package com.faizan.workpilot.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint :
    AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        response?.contentType = "application/json"
        val message = when (authException) {
            is BadCredentialsException ->
                "Invalid or expired access token."
            else ->
                "Authentication required. Please log in to access this resource."
        }
        response?.writer?.write(
            """
        {
            "message":"$message"
        }
        """.trimIndent()
        )
    }

}