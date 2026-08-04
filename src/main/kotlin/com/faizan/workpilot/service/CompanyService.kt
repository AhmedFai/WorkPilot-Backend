package com.faizan.workpilot.service

import com.faizan.workpilot.dto.request.CreateCompanyRequest
import com.faizan.workpilot.dto.request.UpdateCompanyRequest
import com.faizan.workpilot.dto.response.CompanyResponse
import com.faizan.workpilot.entity.Company
import com.faizan.workpilot.exception.CompanyAlreadyExistsException
import com.faizan.workpilot.exception.CompanyNotFoundException
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

    fun getAllCompanies(): List<CompanyResponse> {
        val companies = companyRepository.findAllByIsActiveTrue()
        return companies.map { it.toResponse() }
    }

    fun getCompanyById(id: Long): CompanyResponse {
        val company = companyRepository.findById(id).orElseThrow {
            CompanyNotFoundException("Company with id $id not found")
        }
        return company.toResponse()
    }

    fun updateCompany(
        id: Long,
        request: UpdateCompanyRequest
    ): CompanyResponse {
        val company = companyRepository.findById(id).orElseThrow {
            CompanyNotFoundException("Company with id $id not found")
        }
        company.name = request.name
        company.email = request.email
        company.website = request.website
        val updatedCompany = companyRepository.save(company)
        return updatedCompany.toResponse()
    }

    fun deletedCompany(
        id: Long
    ): CompanyResponse {
        val company = companyRepository.findById(id).orElseThrow {
            CompanyNotFoundException("Company with id $id is not found")
        }
        company.isActive = false
        val deletedCompany = companyRepository.save(company)
        return deletedCompany.toResponse()
    }

}