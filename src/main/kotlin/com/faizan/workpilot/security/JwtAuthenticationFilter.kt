package com.faizan.workpilot.security

import com.faizan.workpilot.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
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

        val jwt = authHeader.substring(7)
        val email = jwtService.extractEmail(jwt)
        val user = userRepository.findByEmail(email)

        if (user == null) {
            filterChain.doFilter(request, response)
            return
        }

        if (jwtService.validateToken(jwt, user)) {

            val authentication =
                UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    emptyList()
                )

            //Todo: In future for professional we have to do improvement instead of emptyList
//            listOf(
//                SimpleGrantedAuthority(
//                    "ROLE_${user.role}"
//                )
//            )

            SecurityContextHolder
                .getContext()
                .authentication = authentication
        }

        filterChain.doFilter(request, response)

    }

}