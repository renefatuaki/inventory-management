package dev.elfa.inventorymanagement.controller

import dev.elfa.inventorymanagement.dto.ProductDto
import dev.elfa.inventorymanagement.service.ProductService
import dev.elfa.inventorymanagement.util.logger
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.web.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {
    val logger = logger<ProductController>()

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "ASC") direction: Sort.Direction,
        @RequestParam(defaultValue = "id") sortBy: String
    ): ResponseEntity<PagedModel<ProductDto>> {
        logger.info("GET /api/products")

        val pageable = PageRequest.of(page, size, direction, sortBy)
        val pageableProducts = productService.getPageableProducts(pageable)

        return ResponseEntity.ok(pageableProducts)
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): ResponseEntity<ProductDto> {
        logger.info("GET /api/products/$id")

        val productDto = productService.getProduct(id)

        return ResponseEntity.ok(productDto)
    }

    @PostMapping
    fun addProduct(@RequestBody productDto: ProductDto): ResponseEntity<ProductDto> {
        logger.info("POST /api/products")
        logger.info(productDto.toString())

        val createdProduct = productService.addProduct(productDto)

        return ResponseEntity.ok(createdProduct)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: String, @RequestBody productDto: ProductDto): ResponseEntity<ProductDto> {
        logger.info("PUT /api/products/$id")
        logger.info(productDto.toString())

        val updatedProductDto = productService.updateProduct(id, productDto)

        return ResponseEntity.accepted().body(updatedProductDto)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Unit> {
        logger.info("DELETE /api/products/$id")

        productService.deleteProduct(id)

        return ResponseEntity.noContent().build()
    }
}