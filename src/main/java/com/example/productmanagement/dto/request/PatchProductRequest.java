package com.example.productmanagement.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PatchProductRequest(
        @Size(min = 2, max = 120, message = "Product name must be between 2 and 120 characters")
        String name,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @DecimalMin(value = "0.01", message = "Price must be greater than zero")
        BigDecimal price,

        @Size(max = 100, message = "Category must not exceed 100 characters")
        String category,

        @Min(value = 0, message = "Stock quantity cannot be negative")
        Integer stockQuantity
) {
}
