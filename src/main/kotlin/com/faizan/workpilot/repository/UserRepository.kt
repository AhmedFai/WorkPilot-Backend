package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>{

    fun existsByEmail(email: String): Boolean

}