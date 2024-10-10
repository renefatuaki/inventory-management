package dev.elfa.inventorymanagement.model

data class ErrorMessage(val status: Int, val message: String)

data class FieldError(val field: String, val message: String)