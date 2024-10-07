package dev.elfa.inventorymanagement.dto

data class ProductDto(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)