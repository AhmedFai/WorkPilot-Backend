package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateProjectRequest
import com.faizan.workpilot.dto.request.UpdateProjectRequest
import com.faizan.workpilot.dto.response.ProjectResponse
import com.faizan.workpilot.enums.ActivityType
import com.faizan.workpilot.exception.CompanyNotFoundException
import com.faizan.workpilot.exception.ProjectNotFoundException
import com.faizan.workpilot.exception.UserNotFoundException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.CompanyRepository
import com.faizan.workpilot.repository.ProjectRepository
import com.faizan.workpilot.repository.UserRepository
import com.faizan.workpilot.security.CurrentUserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository,
    private val activityService: ActivityService,
    private val currentUserService: CurrentUserService
) {

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'PROJECT_HEAD')")
    @Transactional
    fun createProject(
        request: CreateProjectRequest
    ): ProjectResponse {

        val company = companyRepository.findById(request.companyId)
            .orElseThrow {
                CompanyNotFoundException(
                    "Company with id ${request.companyId} not found"
                )
            }

        val projectHead = userRepository.findById(request.projectHeadId)
            .orElseThrow {
                UserNotFoundException(
                    "User with id ${request.projectHeadId} not found"
                )
            }

        val project = request.toEntity(company, projectHead)

        val savedProject = projectRepository.save(project)

        activityService.createActivity(
            company = company,
            type = ActivityType.PROJECT_CREATED,
            message = "New project created",
            description = "${savedProject.name} was created and assigned to the company.",
            performedBy = currentUserService.getCurrentUser()
        )

        return savedProject.toResponse()
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun getAllProjects(): List<ProjectResponse> {
        val project = projectRepository.findAllByIsActiveTrue()
        return project.map { it.toResponse() }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun getProjectById(id: Long): ProjectResponse{
        val project = projectRepository.findById(id)
            .orElseThrow {
                ProjectNotFoundException("Project with id $id not found")
            }

        return project.toResponse()
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROJECT_HEAD')")
    @Transactional
    fun updateProject(id: Long, request: UpdateProjectRequest): ProjectResponse {
        val project = projectRepository.findById(id)
            .orElseThrow {
                ProjectNotFoundException("Project with id $id not found")
            }
        val company = companyRepository.findById(request.companyId)
            .orElseThrow {
                CompanyNotFoundException("Company with id ${request.companyId} not found")
            }
        val projectHead = userRepository.findById(request.projectHeadId)
            .orElseThrow {
                UserNotFoundException("User with id ${request.projectHeadId} not found")
            }

        project.name = request.name
        project.description = request.description
        project.company = company
        project.projectHead = projectHead
        val updatedProject = projectRepository.save(project)
        return updatedProject.toResponse()
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    fun deleteProject(id: Long): ProjectResponse {
        val deleteProject = projectRepository.findById(id)
            .orElseThrow {
                ProjectNotFoundException("Project with id $id not found")
            }
        deleteProject.isActive = false
        val deletedProject = projectRepository.save(deleteProject)
        return deletedProject.toResponse()
    }

}