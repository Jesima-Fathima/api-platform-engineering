package com.example.productmanagement.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateProductRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationForValidProductRequest() {
        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1299.99"),
                "Electronics",
                10
        );

        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationForInvalidProductRequest() {
        CreateProductRequest request = new CreateProductRequest(
                "",
                "",
                new BigDecimal("0.00"),
                "",
                -1
        );

        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .anySatisfy(message -> assertThat(message).containsAnyOf("Product name is required", "Price must be greater than zero", "Category is required", "Stock quantity cannot be negative"));
    }
}
