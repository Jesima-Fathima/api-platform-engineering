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

## Milestone 2: Product REST API foundation

This milestone adds the HTTP-facing layer for the Product domain, including DTO-driven validation and a clean service-controller separation.

### Included in this milestone

- ProductController with CRUD endpoints under `/api/v1/products`
- ProductService with business logic and transactional boundaries
- Create, update, and patch request DTOs
- Product response DTO for entity-to-API mapping
- Centralized validation and not-found exception handling
- Pagination, sorting, and category filtering support on list queries
- Unit and integration tests covering service and controller behavior

### Why this matters

This is the first point where the application becomes a real API instead of just a data-access project. It demonstrates the classic request flow:

1. HTTP request enters the controller
2. DTO validation occurs before processing
3. Service enforces business rules
4. Repository persists or reads the entity
5. Mapper converts the entity to a response DTO
6. `@RestControllerAdvice` returns consistent error payloads

### Key design decisions

- DTOs are used to decouple the API contract from the persistence model.
- Validation is placed at the API boundary using Bean Validation.
- Constructor injection keeps dependencies explicit and testable.
- The service layer owns business logic and transactional behavior.
- Exception handling is centralized to keep controller code focused.

### Product API endpoints

- `POST /api/v1/products`
- `GET /api/v1/products`
- `GET /api/v1/products/{id}`
- `PUT /api/v1/products/{id}`
- `PATCH /api/v1/products/{id}`
- `DELETE /api/v1/products/{id}`

### Current status

Milestone 1 and Milestone 2 are both in place and verified with passing tests.

---
