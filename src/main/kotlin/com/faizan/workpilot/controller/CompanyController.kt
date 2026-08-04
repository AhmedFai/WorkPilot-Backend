package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateCompanyRequest
import com.faizan.workpilot.dto.request.UpdateCompanyRequest
import com.faizan.workpilot.dto.response.CompanyResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.CompanyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/companies")
class CompanyController(
    private val companyService: CompanyService
) {

    @PostMapping
    fun createCompany(
        @Valid
        @RequestBody request: CreateCompanyRequest
    ): ResponseEntity<SuccessResponse<CompanyResponse>> {
        val savedCompany = companyService.createCompany(request)
        return status(HttpStatus.CREATED).body(
            SuccessResponse(
                message = "Company created successfully",
                data = savedCompany
            )
        )
    }

    @GetMapping
    fun getAllCompanies(): ResponseEntity<SuccessResponse<List<CompanyResponse>>> {
        val getAllCompanies = companyService.getAllCompanies()
        return status(HttpStatus.OK).body(
            SuccessResponse(
                message = "Companies fetched successfully",
                data = getAllCompanies
            )
        )
    }

    @GetMapping("/{id}")
    fun getCompanyById(
        @PathVariable id: Long
    ): ResponseEntity<SuccessResponse<CompanyResponse>> {
        val company = companyService.getCompanyById(id)
        //Todo: GET By Id ko findByIdAndIsActiveTrue() se improve karna
        return status(HttpStatus.OK).body(
            SuccessResponse(
                message = "Company fetched successfully",
                data = company
            )
        )
    }

    @PutMapping("/{id}")
    fun updateCompany(
        @PathVariable id: Long,
        @Valid
        @RequestBody request: UpdateCompanyRequest
    ): ResponseEntity<SuccessResponse<CompanyResponse>> {
        val updatedCompany = companyService.updateCompany(id, request)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Company updated successfully",
                data = updatedCompany
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCompany(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<CompanyResponse>> {
        val deletedCompany = companyService.deletedCompany(id)
        return ResponseEntity.ok(
            SuccessResponse(
                message = "Company deleted successfully",
                data = deletedCompany
            )
        )
    }

}