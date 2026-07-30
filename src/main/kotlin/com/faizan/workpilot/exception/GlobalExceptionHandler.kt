package com.faizan.workpilot.exception

import com.faizan.workpilot.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                message = "Validation failed",
                errors = errors
            )
        )
    }

    @ExceptionHandler(CompanyAlreadyExistsException::class)
    fun handleCompanyAlreadyExistsException(ex: CompanyAlreadyExistsException): ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                message = "Company already registered",
                errors = mapOf(
                    "email" to (ex.message ?: "Email already registered")
                )
            )
        )
    }

}