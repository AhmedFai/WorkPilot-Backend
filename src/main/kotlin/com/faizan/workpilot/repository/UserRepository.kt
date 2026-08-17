package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.User
import com.faizan.workpilot.enums.Role
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User, Long>{

    fun existsByEmail(email: String): Boolean

    fun findAllByIsActiveTrue(): List<User>

    fun findByEmail(email: String): User?

    fun countByCompanyIdAndRoleAndIsActiveTrue(
        companyId: Long,
        role: Role
    ): Long

    @EntityGraph(attributePaths = ["company"])
    fun findWithCompanyById(id: Long): Optional<User>

    // TODO: for all the entities
    //- Replace @Transactional workaround with EntityGraph / Fetch Join
    //- Hide soft-deleted records in findById()
}