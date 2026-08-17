package com.faizan.workpilot.security

import com.faizan.workpilot.entity.User
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CurrentUserService(
    private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun getCurrentUser(): User {

        val authentication =
            SecurityContextHolder.getContext().authentication

        val user = authentication.principal as User

        return userRepository.findWithCompanyById(user.id!!)
            .orElseThrow {
                UserNotFoundException("Current user not found")
            }
    }
}