package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateCompanyRequest
import com.faizan.workpilot.dto.response.CompanyResponse
import com.faizan.workpilot.exception.CompanyAlreadyExistsException
import com.faizan.workpilot.mapper.toEntity
import com.faizan.workpilot.mapper.toResponse
import com.faizan.workpilot.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository
) {

    fun createCompany(request: CreateCompanyRequest): CompanyResponse {
        if (companyRepository.existsByEmail(request.email)) {
            throw CompanyAlreadyExistsException("Email already registered")
        }
        val company = request.toEntity()
        val savedCompany = companyRepository.save(company)
        return savedCompany.toResponse()
    }

}