package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateProjectRequest
import com.faizan.workpilot.dto.response.ProjectResponse
import com.faizan.workpilot.exception.CompanyNotFoundException
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.CompanyRepository
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository
) {

    fun createProject(
        request: CreateProjectRequest
    ): ProjectResponse {
        val company = companyRepository.findById(request.companyId)
            .orElseThrow {
                CompanyNotFoundException("Company with id ${request.companyId} not found")
            }
        val projectHead = userRepository.findById(request.projectHeadId)
            .orElseThrow {
                UserNotFoundException("User with id ${request.projectHeadId} not found")
            }
        val project = request.toEntity(company,projectHead)
        val savedProject = projectRepository.save(project)
        return savedProject.toResponse()
    }

}