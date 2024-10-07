package dev.elfa.inventorymanagement.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
data class Product(
    @Id @GeneratedValue
    var id: Long? = null,

    @field:NotBlank
    var name: String = "",

    @field:NotBlank
    var description: String = "",

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    var price: Double = 0.0,

    @field:NotNull
    @field:Min(value = 0)
    var stock: Int = 0
)