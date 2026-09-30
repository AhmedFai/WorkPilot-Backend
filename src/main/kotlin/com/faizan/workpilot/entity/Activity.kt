package com.faizan.workpilot.entity

import com.faizan.workpilot.enums.ActivityType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "activities")
class Activity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company,

    @Enumerated(EnumType.STRING)
    var type: ActivityType,

    @Column(nullable = false)
    var message: String,

    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_id")
    var performedBy: User? = null,

    var createdAt: LocalDateTime = LocalDateTime.now()
)