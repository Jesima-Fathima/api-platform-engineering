package com.example.productmanagement.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String category,
        Integer stockQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
