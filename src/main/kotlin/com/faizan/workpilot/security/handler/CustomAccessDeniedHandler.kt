package com.faizan.workpilot.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.status =
            HttpServletResponse.SC_FORBIDDEN
        response?.writer?.write(
            """
        {
            "message":"Access denied. You don't have permission to perform this action."
        }
        """.trimIndent()
        )
    }
}