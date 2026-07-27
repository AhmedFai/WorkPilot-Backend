package com.faizan.workpilot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkpilotBackendApplication

fun main(args: Array<String>) {
	runApplication<WorkpilotBackendApplication>(*args)
}
