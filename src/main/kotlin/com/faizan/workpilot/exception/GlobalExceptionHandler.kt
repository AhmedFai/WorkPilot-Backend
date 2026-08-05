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

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                message = "User already registered",
                errors = mapOf(
                    "email" to (ex.message ?: "Email already registered")
                )
            )
        )
    }

    @ExceptionHandler(CompanyNotFoundException::class)
    fun handleCompanyNotFoundException(
        ex: CompanyNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                message = "Company not found",
                errors = mapOf(
                    "companyId" to (ex.message ?: "Company not found")
                )
            )
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        ex: UserNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = ex.message!!
                )
            )
    }

    @ExceptionHandler(ProjectNotFoundException::class)
    fun handleProjectNotFoundException(
        ex: ProjectNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = ex.message!!
                )
            )
    }

    @ExceptionHandler(TaskNotFoundException::class)
    fun handleTaskNotFoundException(
        ex: TaskNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = ex.message!!
                )
            )
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(
        ex: InvalidCredentialsException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ErrorResponse(
                    message = ex.message!!
                )
            )
    }

    @ExceptionHandler(AccountDisabledException::class)
    fun handleAccountDisabledException(
        ex: AccountDisabledException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ErrorResponse(
                message = ex.message ?: "Account is disabled",
                errors = null
            )
        )
    }

}