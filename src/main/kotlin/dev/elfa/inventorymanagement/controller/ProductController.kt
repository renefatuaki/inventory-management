package dev.elfa.inventorymanagement.controller

import dev.elfa.inventorymanagement.dto.ProductDto
import dev.elfa.inventorymanagement.model.Product
import dev.elfa.inventorymanagement.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.web.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "ASC") direction: Sort.Direction,
        @RequestParam(defaultValue = "id") sortBy: String
    ): ResponseEntity<PagedModel<ProductDto>> {
        val pageable = PageRequest.of(page, size, direction, sortBy)
        val pageableProducts = productService.getPageableProducts(pageable)

        return ResponseEntity.ok(pageableProducts)
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): ResponseEntity<ProductDto> {
        val productDto = productService.getProduct(id)

        return ResponseEntity.ok(productDto)
    }

    @PostMapping
    fun addProduct(@RequestBody @Valid product: Product): ResponseEntity<Product> {
        val createdProduct = productService.addProduct(product)

        return ResponseEntity.ok(createdProduct)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: String, @RequestBody @Valid product: Product): ResponseEntity<ProductDto> {
        val productDto = productService.updateProduct(id, product)

        return ResponseEntity.accepted().body(productDto)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Unit> {
        productService.deleteProduct(id)

        return ResponseEntity.noContent().build()
    }
}