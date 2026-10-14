package com.example.productmanagement.mapper;

import com.example.productmanagement.dto.request.CreateProductRequest;
import com.example.productmanagement.dto.request.UpdateProductRequest;
import com.example.productmanagement.dto.response.ProductResponse;
import com.example.productmanagement.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(CreateProductRequest request) {
        return new Product(
                request.name(),
                request.description(),
                request.price(),
                request.category(),
                request.stockQuantity()
        );
    }

    public static Product toEntity(UpdateProductRequest request) {
        return new Product(
                request.name(),
                request.description(),
                request.price(),
                request.category(),
                request.stockQuantity()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getStockQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
