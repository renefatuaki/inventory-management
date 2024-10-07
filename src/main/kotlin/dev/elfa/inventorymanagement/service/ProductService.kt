package dev.elfa.inventorymanagement.service

import dev.elfa.inventorymanagement.dto.ProductDto
import dev.elfa.inventorymanagement.exception.ProductNotFoundException
import dev.elfa.inventorymanagement.model.Product
import dev.elfa.inventorymanagement.repository.ProductRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getPageableProducts(pageable: Pageable): PagedModel<ProductDto> {
        val pageableProducts = productRepository.findAll(pageable)
        val pageableProductDtos = pageableProducts.map { ProductDto(it.id!!, it.name, it.description, it.price, it.stock) }

        return PagedModel(pageableProductDtos)
    }

    fun addProduct(product: Product): Product = productRepository.save<Product>(product)

    fun getProduct(id: String): ProductDto {
        val product = productRepository.findById(id.toLong()).orElseThrow {
            ProductNotFoundException("Product not found with id: $id")
        }

        return ProductDto(product.id!!, product.name, product.description, product.price, product.stock)
    }

    fun updateProduct(id: String, updatedProduct: Product): ProductDto? {
        val product = productRepository.findById(id.toLong()).orElseThrow {
            ProductNotFoundException("Product not found with id: $id")
        }

        product.apply {
            name = updatedProduct.name
            description = updatedProduct.description
            price = updatedProduct.price
            stock = updatedProduct.stock
        }

        val savedProduct = productRepository.save(product)

        return ProductDto(
            savedProduct.id!!,
            savedProduct.name,
            savedProduct.description,
            savedProduct.price,
            savedProduct.stock
        )
    }

    fun deleteProduct(id: String) {
        if (!productRepository.existsById(id.toLong())) {
            throw ProductNotFoundException("Product not found with id: $id")
        }

        productRepository.deleteById(id.toLong())
    }
}