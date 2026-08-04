package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateUserRequest
import com.faizan.workpilot.dto.request.UpdateUserRequest
import com.faizan.workpilot.dto.response.UserResponse
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.exception.CompanyAlreadyExistsException
import com.faizan.workpilot.exception.CompanyNotFoundException
import com.faizan.workpilot.exception.UserAlreadyExistsException
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.CompanyRepository
import com.faizan.workpilot.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun getAllUsers(): List<UserResponse> {
        val users = userRepository.findAllByIsActiveTrue()
        return users.map { it.toResponse() }
    }

    @Transactional
    fun getUserById(id: Long): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            UserNotFoundException("User with is $id not found")
        }
        return user.toResponse()
    }

    @Transactional
    fun updateUser(
        id: Long,
        request: UpdateUserRequest
    ): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            UserNotFoundException("User with id $id not found")
        }
        val company = companyRepository.findById(request.companyId)
            .orElseThrow {
                CompanyNotFoundException("Company with id ${request.companyId} not found")
            }
        user.firstName = request.firstName
        user.lastName = request.lastName
        user.email = request.email
        user.phoneNumber = request.phoneNumber
        user.designation = request.designation
        user.role = request.role
        user.company = company
        val updatedUser = userRepository.save(user)
        return updatedUser.toResponse()
    }

    @Transactional
    fun deleteUser(
        id: Long
    ): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            UserNotFoundException("User with is $id not found")
        }
        user.isActive = false
        val deletedUser = userRepository.save(user)
        return deletedUser.toResponse()
    }

}