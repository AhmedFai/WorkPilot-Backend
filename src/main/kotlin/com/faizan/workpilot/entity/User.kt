package com.faizan.workpilot.entity

import com.faizan.workpilot.enums.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    @Column(unique = true)
    var email: String,
    var password: String,
    var phoneNumber: String,
    var designation: String,
    @Enumerated(EnumType.STRING)
    var role: Role,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company,
    var isActive: Boolean = true,
    var createdAt: LocalDateTime = LocalDateTime.now()
)
