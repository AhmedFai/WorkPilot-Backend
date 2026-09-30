package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.User
import com.faizan.workpilot.enums.Role
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<User, Long>{

    fun existsByEmail(email: String): Boolean

    fun findAllByIsActiveTrue(): List<User>

    fun findByEmail(email: String): User?

    @EntityGraph(attributePaths = ["company"])
    fun findWithCompanyByEmail(email: String): User?

    fun countByCompanyIdAndRoleAndIsActiveTrue(
        companyId: Long,
        role: Role
    ): Long

    @EntityGraph(attributePaths = ["company"])
    fun findWithCompanyById(id: Long): Optional<User>

    fun countByCompanyIdAndIsActiveTrue(
        companyId: Long
    ): Long

    fun findTop5ByCompanyIdAndRoleAndIsActiveTrueOrderByCreatedAtDesc(
        companyId: Long,
        role: Role
    ): List<User>

    @Query("""
    SELECT u FROM User u
    WHERE u.company.id = :companyId
    AND u.role = :role
    ORDER BY u.createdAt DESC
""")
    fun findCompanyAdmins(
        @Param("companyId") companyId: Long,
        @Param("role") role: Role,
        pageable: Pageable
    ): Page<User>


    @Query("""
    SELECT u FROM User u
    WHERE u.company.id = :companyId
    AND u.role = :role
    AND (
        LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
    )
    ORDER BY u.createdAt DESC
""")
    fun searchCompanyAdmins(
        @Param("companyId") companyId: Long,
        @Param("role") role: Role,
        @Param("search") search: String,
        pageable: Pageable
    ): Page<User>

    @EntityGraph(attributePaths = ["company"])
    @Query("""
    SELECT u FROM User u
    WHERE u.id = :adminId
    AND u.company.id = :companyId
    AND u.role = :role
""")
    fun findCompanyAdminById(
        @Param("adminId") adminId: Long,
        @Param("companyId") companyId: Long,
        @Param("role") role: Role
    ): User?

    // TODO: for all the entities
    //- Replace @Transactional workaround with EntityGraph / Fetch Join
    //- Hide soft-deleted records in findById()
}