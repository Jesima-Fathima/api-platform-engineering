# api-platform-engineering
Hands-on API engineering with Java and Spring Boot, covering REST API design, authentication, authorization, OAuth 2.0, JWT, API gateways, security, and production readiness.

## Project learning phases

This portfolio project is intentionally split into phases so each topic is learned in a practical sequence:

1. Product REST API foundation
2. HTTP fundamentals: REST, status codes, headers, idempotency, caching, versioning
3. Spring Security, authentication, authorization, JWT, roles, and permissions
4. OAuth 2.0 and OpenID Connect with providers and token flows
5. API Gateway patterns: routing, filtering, rate limiting, quotas, and security
6. Microservice communication: propagation, retries, timeouts, circuit breakers, and discovery
7. API security and production readiness: OWASP API Security, tracing, metrics, logging, and performance tuning
8. Advanced API topics: webhooks, GraphQL, gRPC, contract testing, and lifecycle management

## Milestone 1: Project bootstrap and domain setup

This milestone establishes the initial Spring Boot 3 Java 21 project foundation without introducing security, OAuth, or gateway code.

### Included in this milestone

- Java 21 project configuration with Maven
- Spring Boot 3.3.5-compatible dependency set
- H2 in-memory database configuration for local development and testing
- Product JPA entity with validation constraints
- Product repository with pagination and category-based query support
- Smoke tests to confirm application context and repository behavior

### Why this matters

A strong API foundation depends on clean project structure, correct dependency compatibility, and verified persistence before adding controllers, validation, or business logic.

### Key design decisions

- Java 21 is used to align with modern Spring Boot 3 capabilities.
- Spring Boot BOM-based dependency management avoids conflicting framework versions.
- H2 is used for local development and fast repository verification.
- The Product entity is kept simple and focused on persistence while preserving validation rules that will be reused later in API DTOs.
- The repository includes a category-based query method because it supports future list filtering without extra abstraction.

### How to run the project

```bash
mvn clean test
mvn spring-boot:run
```

The application will start on port 8080 and expose the H2 console at `/h2-console`.

### Current status

The initial project skeleton is built and verified with a passing test suite for the application context and JPA repository behavior.

---
