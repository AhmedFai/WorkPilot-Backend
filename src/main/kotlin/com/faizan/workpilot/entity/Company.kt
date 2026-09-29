package com.faizan.workpilot.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "companies")
class Company(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    var email: String,

    var phone: String? = null,

    var website: String? = null,

    var addressLine1: String? = null,

    var addressLine2: String? = null,

    var city: String? = null,

    var state: String? = null,

    var postalCode: String? = null,

    var country: String? = null,

    var isActive: Boolean = true,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()
)