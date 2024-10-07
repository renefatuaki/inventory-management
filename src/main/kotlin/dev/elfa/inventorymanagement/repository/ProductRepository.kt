package dev.elfa.inventorymanagement.repository

import dev.elfa.inventorymanagement.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>