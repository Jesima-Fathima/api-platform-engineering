package com.example.productmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.productmanagement.dto.request.CreateProductRequest;
import com.example.productmanagement.dto.request.PatchProductRequest;
import com.example.productmanagement.dto.request.UpdateProductRequest;
import com.example.productmanagement.dto.response.ProductResponse;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.ProductNotFoundException;
import com.example.productmanagement.mapper.ProductMapper;
import com.example.productmanagement.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(String category, Pageable pageable) {
        Page<Product> products;

        if (category == null || category.isBlank()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findByCategory(category, pageable);
        }

        return products.map(ProductMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(request.category());
        product.setStockQuantity(request.stockQuantity());

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse patchProduct(Long id, PatchProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (request.name() != null) {
            product.setName(request.name());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }
        if (request.category() != null) {
            product.setCategory(request.category());
        }
        if (request.stockQuantity() != null) {
            product.setStockQuantity(request.stockQuantity());
        }

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
