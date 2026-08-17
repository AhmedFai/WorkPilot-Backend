package com.faizan.workpilot.entity

import com.faizan.workpilot.enums.ProjectStatus
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
@Table(name = "projects")
class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    var description: String? = null,
    @Enumerated(EnumType.STRING)
    var status: ProjectStatus = ProjectStatus.PLANNED,
    @ManyToOne
    @JoinColumn(name = "company_id")
    var company: Company,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_head_id")
    var projectHead: User,
    var isActive: Boolean = true,
    var createdAt: LocalDateTime = LocalDateTime.now()
)