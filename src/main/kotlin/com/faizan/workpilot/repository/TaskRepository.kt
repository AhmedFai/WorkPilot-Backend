package com.faizan.workpilot.repository

import com.faizan.workpilot.entity.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository: JpaRepository<Task, Long>