package dev.elfa.inventorymanagement.service

import dev.elfa.inventorymanagement.dto.ProductDto
import dev.elfa.inventorymanagement.exception.ProductNotFoundException
import dev.elfa.inventorymanagement.model.Product
import dev.elfa.inventorymanagement.repository.ProductRepository
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductServiceTest {
    private val productRepository: ProductRepository = mock()
    private val productService: ProductService = ProductService(productRepository)

    @Test
    fun getPageableProducts_FirstPageWithProducts_ReturnsPageableProducts() {
        val pageable: Pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "id")
        val products = listOf(
            Product(1L, "A", "...", 10.0, 50),
            Product(2L, "B", "...", 20.0, 100)
        )

        val page = PageImpl(products, pageable, products.size.toLong())

        whenever(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getPageableProducts(pageable)

        assertEquals(2, result.content.size)
        assertEquals("A", result.content[0].name)
        assertEquals("B", result.content[1].name)
    }

    @Test
    fun addProduct_ValidProduct_ReturnsSavedProduct() {
        val productDto = ProductDto(null, "A", "...", 10.0, 50)
        val product = Product(1L, "A", "...", 10.0, 50)

        whenever(productRepository.save(any())).thenReturn(product)

        val result = productService.addProduct(productDto)

        assertTrue { result == product.toDto() }
    }

    @Test
    fun getProduct_ProductExists_ReturnsProductDto() {
        val product = Product(1L, "A", "...", 10.0, 50)

        whenever(productRepository.findById(1L)).thenReturn(Optional.of(product))

        val result = productService.getProduct("1")

        assertTrue { result == product.toDto() }
    }

    @Test
    fun getProduct_ProductDoesNotExist_ThrowsProductNotFoundException() {
        val id = "1"
        whenever(productRepository.findById(id.toLong())).thenReturn(Optional.empty())

        val exception = assertThrows<ProductNotFoundException> { productService.getProduct(id) }

        assertEquals("Product not found with id: $id", exception.message)
    }

    @Test
    fun updateProduct_ProductExists_UpdatesAndReturnsProductDto() {
        val existingProduct = Product(1L, "A", "...", 10.0, 50)
        val productDto = ProductDto(null, "A", "...", 15.0, 25)
        val savedProduct = Product(1L, "A", "...", 15.0, 25)
        val expectedResult = savedProduct.toDto()

        whenever(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct))
        whenever(productRepository.save(any<Product>())).thenReturn(savedProduct)

        val result = productService.updateProduct("1", productDto)

        assertEquals(expectedResult, result)
    }

    @Test
    fun updateProduct_ProductDoesNotExists_ThrowsProductNotFoundException() {
        val id = "1"
        val updatedProduct = ProductDto(null, "A", "...", 15.0, 25)

        whenever(productRepository.findById(id.toLong())).thenReturn(Optional.empty())

        val exception = assertThrows<ProductNotFoundException> {
            productService.updateProduct(id, updatedProduct)
        }

        assertEquals("Product not found with id: $id", exception.message)
    }

    @Test
    fun deleteProduct_ProductExists_DeletesProduct() {
        whenever(productRepository.existsById(1L)).thenReturn(true)
        doNothing().whenever(productRepository).deleteById(1L)

        productService.deleteProduct("1")

        verify(productRepository, times(1)).deleteById(1L)
    }

    @Test
    fun deleteProduct_ProductDoesNotExists_IgnoreCase() {
        val id = "1"
        whenever(productRepository.existsById(id.toLong())).thenReturn(false)

        productService.deleteProduct(id)

        verify(productRepository, times(1)).deleteById(1L)
    }
}