package com.faizan.workpilot.security

import com.faizan.workpilot.repository.UserRepository
import com.faizan.workpilot.security.handler.CustomAuthenticationEntryPoint
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val jwt = authHeader.substring(7)
            val email =
                jwtService.extractAccessEmail(jwt)
            val user = userRepository.findByEmail(email)

            if (user == null) {
                filterChain.doFilter(request, response)
                return
            }

            if (jwtService.validateAccessToken(jwt, user)) {

                val authentication =
                    UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        listOf(
                            SimpleGrantedAuthority(
                                "ROLE_${user.role}"
                            )
                        )
                    )

                SecurityContextHolder
                    .getContext()
                    .authentication = authentication
            }

            filterChain.doFilter(request, response)
        }catch (ex: JwtException) {
            authenticationEntryPoint.commence(
                request,
                response,
                BadCredentialsException(
                    "Invalid or expired access token.",
                    ex
                )
            )
        }

    }

}