package com.example.productmanagement.repository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.example.productmanagement.entity.Product;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProductById() {
        Product product = new Product(
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1299.99"),
                "Electronics",
                10
        );

        Product saved = productRepository.save(product);

        assertThat(saved.getId()).isNotNull();
        assertThat(productRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void shouldFindProductsByCategory() {
        Product product = new Product(
                "Smartphone",
                "Latest model",
                new BigDecimal("899.00"),
                "Electronics",
                25
        );

        productRepository.save(product);

        Page<Product> result = productRepository.findByCategory("Electronics", PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Smartphone");
    }
}
