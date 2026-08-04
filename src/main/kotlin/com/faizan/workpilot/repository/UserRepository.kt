package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>{

    fun existsByEmail(email: String): Boolean

    fun findAllByIsActiveTrue(): List<User>

    // TODO: for all the entities
    //- Replace @Transactional workaround with EntityGraph / Fetch Join
    //- Hide soft-deleted records in findById()
}