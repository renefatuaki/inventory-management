package dev.elfa.inventorymanagement.exception

import dev.elfa.inventorymanagement.model.ErrorMessage
import dev.elfa.inventorymanagement.model.FieldError
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleRuntimeExceptionException(exception: RuntimeException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.message ?: "Internal Server Error"
        )

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleProductNotFoundException(exception: ProductNotFoundException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            exception.message ?: "Product not found"
        )

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleSaveExceptionException(exception: SaveException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.message ?: "Could not save entity"
        )

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleValidationException(exception: ConstraintViolationException): ResponseEntity<List<FieldError>> {
        val errors = exception.constraintViolations.map { error ->
            val field = error.propertyPath.toString().substringAfterLast(".")
            val message = error.message ?: "Invalid value"

            FieldError(field, message)
        }

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}