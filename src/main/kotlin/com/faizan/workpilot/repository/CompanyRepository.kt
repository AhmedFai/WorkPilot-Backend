package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository: JpaRepository<Company, Long>