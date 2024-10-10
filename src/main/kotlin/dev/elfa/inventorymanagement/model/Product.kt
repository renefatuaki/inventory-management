package dev.elfa.inventorymanagement.model

import dev.elfa.inventorymanagement.dto.ProductDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Product(
    @Id @GeneratedValue
    var id: Long? = null,
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var stock: Int = 0
) {
    fun toDto(): ProductDto = ProductDto(this.id, this.name, this.description, this.price, this.stock)
}