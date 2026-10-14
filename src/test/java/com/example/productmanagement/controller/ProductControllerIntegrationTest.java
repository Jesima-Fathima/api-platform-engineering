package com.example.productmanagement.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.productmanagement.dto.request.CreateProductRequest;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1299.99"),
                "Electronics",
                10
        );

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    void shouldReturnBadRequestForInvalidPrice() throws Exception {
        String payload = "{\"name\":\"Laptop\",\"description\":\"Gaming laptop\",\"price\":0.00,\"category\":\"Electronics\",\"stockQuantity\":10}";

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetProductsByCategory() throws Exception {
        productRepository.save(new Product("Laptop", "Gaming laptop", new BigDecimal("1299.99"), "Electronics", 10));

        mockMvc.perform(get("/api/v1/products")
                        .param("category", "Electronics")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Laptop"));
    }
}
