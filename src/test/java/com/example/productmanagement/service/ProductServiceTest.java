package com.example.productmanagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.productmanagement.dto.request.CreateProductRequest;
import com.example.productmanagement.dto.request.PatchProductRequest;
import com.example.productmanagement.dto.request.UpdateProductRequest;
import com.example.productmanagement.dto.response.ProductResponse;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.ProductNotFoundException;
import com.example.productmanagement.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1299.99"),
                "Electronics",
                10
        );

        Product product = new Product("Laptop", "Gaming laptop", new BigDecimal("1299.99"), "Electronics", 10);
        product.setStockQuantity(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(request);

        assertThat(response.name()).isEqualTo("Laptop");
        assertThat(response.category()).isEqualTo("Electronics");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldGetProductById() {
        Product product = new Product("Tablet", "Android tablet", new BigDecimal("499.99"), "Electronics", 5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response.id()).isNull();
        assertThat(response.name()).isEqualTo("Tablet");
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldReturnProductsForCategory() {
        Product product = new Product("Phone", "Smartphone", new BigDecimal("699.00"), "Electronics", 20);

        Page<Product> page = new PageImpl<>(List.of(product));
        when(productRepository.findByCategory("Electronics", PageRequest.of(0, 10))).thenReturn(page);

        Page<ProductResponse> response = productService.getProducts("Electronics", PageRequest.of(0, 10));

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).name()).isEqualTo("Phone");
    }

    @Test
    void shouldUpdateProduct() {
        Product product = new Product("Keyboard", "Mechanical keyboard", new BigDecimal("89.99"), "Accessories", 7);
        UpdateProductRequest request = new UpdateProductRequest("Keyboard", "Mechanical keyboard", new BigDecimal("99.99"), "Accessories", 12);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.updateProduct(1L, request);

        assertThat(response.price()).isEqualByComparingTo(new BigDecimal("99.99"));
        assertThat(response.stockQuantity()).isEqualTo(12);
    }

    @Test
    void shouldPatchProduct() {
        Product product = new Product("Monitor", "4K Monitor", new BigDecimal("299.99"), "Electronics", 15);
        PatchProductRequest request = new PatchProductRequest("4K Gaming Monitor", null, null, null, 18);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.patchProduct(1L, request);

        assertThat(response.name()).isEqualTo("4K Gaming Monitor");
        assertThat(response.stockQuantity()).isEqualTo(18);
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product("Mouse", "Wireless mouse", new BigDecimal("39.99"), "Accessories", 30);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }
}
