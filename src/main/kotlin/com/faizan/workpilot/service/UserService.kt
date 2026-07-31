package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateUserRequest
import com.faizan.workpilot.dto.response.UserResponse
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.exception.CompanyAlreadyExistsException
import com.faizan.workpilot.exception.CompanyNotFoundException
import com.faizan.workpilot.exception.UserAlreadyExistsException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.CompanyRepository
import com.faizan.workpilot.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository
) {

    fun createUser(
        request: CreateUserRequest
    ): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("Email already registered")
        }
        val company = companyRepository.findById(request.companyId)
            .orElseThrow {
                CompanyNotFoundException("Company with id ${request.companyId} not found")
            }
        val user = request.toEntity(company)
        val savedUser = userRepository.save(user)
        return savedUser.toResponse()
    }

}