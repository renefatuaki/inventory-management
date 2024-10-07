package dev.elfa.inventorymanagement.repository

import dev.elfa.inventorymanagement.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>