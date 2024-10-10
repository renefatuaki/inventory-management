package dev.elfa.inventorymanagement.exception

import dev.elfa.inventorymanagement.model.ErrorMessage
import dev.elfa.inventorymanagement.model.ErrorValidation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

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
    fun handleValidationException(exception: MethodArgumentNotValidException): ResponseEntity<List<ErrorValidation>> {
        val errors = exception.fieldErrors.map { error ->
            val field = error.field
            val message = error.defaultMessage ?: "Invalid value"
            ErrorValidation(field, message)
        }

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}