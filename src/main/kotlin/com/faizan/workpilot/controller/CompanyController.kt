package com.faizan.workpilot.controller

import com.faizan.workpilot.dto.request.CreateCompanyRequest
import com.faizan.workpilot.dto.response.CompanyResponse
import com.faizan.workpilot.dto.response.SuccessResponse
import com.faizan.workpilot.service.CompanyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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

}