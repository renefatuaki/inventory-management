package dev.elfa.inventorymanagement.exception

import dev.elfa.inventorymanagement.model.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
}