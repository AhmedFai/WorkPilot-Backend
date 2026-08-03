package com.faizan.workpilot.entity

import com.faizan.workpilot.enums.Priority
import com.faizan.workpilot.enums.Status
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var description: String? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var assignedTo: User,
    @ManyToOne(fetch = FetchType.LAZY)
    var project: Project,
    @Enumerated(EnumType.STRING)
    var priority: Priority,
    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING,
    var deadline: LocalDateTime = LocalDateTime.now(),
    var isActive: Boolean = true,
    var createdAt: LocalDateTime = LocalDateTime.now()
)