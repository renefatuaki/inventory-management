package dev.elfa.inventorymanagement.dto

import dev.elfa.inventorymanagement.model.Product
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductDto(
    val id: Long?,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    val price: Double,

    @field:NotNull
    @field:Min(value = 0)
    val stock: Int
) {
    fun toProduct(): Product = Product(this.id, this.name, this.description, this.price, this.stock)
}