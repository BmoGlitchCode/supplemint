# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Supplemint is a personal supplement tracker and management system built with Spring Boot 4.0.0 and Java 25. The application follows **Hexagonal Architecture** (Ports and Adapters pattern) to maintain clean separation of concerns and framework independence in the domain layer.

## Build and Development Commands

### Build and Run
```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Package as JAR
./mvnw clean package
```

### Testing
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=ClassName

# Run specific test method
./mvnw test -Dtest=ClassName#methodName
```

### Code Quality
```bash
# Compile only (useful for checking compilation errors)
./mvnw compile

# Clean build artifacts
./mvnw clean
```

## Architecture

This project strictly follows **Hexagonal Architecture**. See `HEXAGONAL_ARCHITECTURE.md` for complete details.

### Core Principles

1. **Domain Layer is Framework-Free**: The domain layer (`domain/`) contains NO Spring annotations, JPA entities, or external framework dependencies. Use pure Java with business logic only.

2. **Dependency Direction**: Dependencies always point inward:
   - Adapters → Application → Domain
   - Never the reverse

3. **Ports Define Contracts**:
   - Input ports (use case interfaces) in `domain/port/input/`
   - Output ports (repository interfaces) in `domain/port/output/`
   - Implementations live in outer layers

### Package Structure by Feature

Code is organized by domain feature (user, supplement, stack), not by layer:

```
com.BmoGlitchCode.supplemint/
├── domain/
│   ├── model/{feature}/          # Domain entities & value objects
│   └── port/
│       ├── input/{feature}/      # Use case interfaces
│       └── output/{feature}/     # Repository interfaces
├── application/
│   ├── usecase/{feature}/        # Use case implementations
│   ├── dto/
│   │   ├── request/{feature}/    # Request DTOs
│   │   └── response/{feature}/   # Response DTOs
│   └── mapper/{feature}/         # DTO mappers
├── adapter/
│   ├── input/rest/{feature}/     # REST controllers
│   └── output/
│       ├── persistence/repository/{feature}/  # Repository implementations
│       └── security/{feature}/   # Security adapters
└── infrastructure/
    └── config/{feature}/         # Spring configuration
```

### Domain Entity Pattern

Domain entities follow this pattern:

```java
// Use Lombok for boilerplate
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entity {
    @EqualsAndHashCode.Include
    private final EntityId id;

    // Static factory method for creation
    public static Entity of(...) {
        // Validation
        // Use EntityId.generate() for new IDs
        return Entity.builder()...build();
    }

    // Domain behavior methods that modify state
    public void updateSomething(...) {
        // Validation
        // Update fields
        this.updatedAt = Instant.now();
    }
}
```

### Value Objects (IDs)

All entity IDs are value objects:

```java
public record EntityId(UUID value) {
    public static EntityId generate() {
        return new EntityId(UUID.randomUUID());
    }

    public static EntityId of(UUID value) {
        Objects.requireNonNull(value, "ID cannot be null");
        return new EntityId(value);
    }
}
```

### Use Case Pattern

Use cases define commands/queries as records and implement input port interfaces:

```java
@RequiredArgsConstructor
public class CreateEntityUseCaseImpl implements CreateEntityUseCase {
    private final EntityRepository repository;

    @Override
    public Entity create(CreateEntityCommand command) {
        // 1. Create domain object using static factory
        // 2. Save via output port
        // 3. Return domain object
    }
}
```

### Configuration Pattern

Each feature has its own configuration class in `infrastructure/config/{feature}/`:

```java
@Configuration
public class EntityUseCaseConfig {
    @Bean
    public CreateEntityUseCase createEntityUseCase(EntityRepository repository) {
        return new CreateEntityUseCaseImpl(repository);
    }
}
```

This pattern manually wires dependencies, keeping the domain and application layers free of Spring annotations.

## Current Implementation Status

### Implemented Features
- **User Management**: Registration and login with in-memory storage
- **Supplement Management**: Full CRUD operations with inventory tracking
- **Stack Management**: Create, update, delete stacks with supplement items

### Data Storage
Currently using **in-memory repositories** (InMemory*Repository classes). These are temporary implementations in `adapter/output/persistence/repository/{feature}/`.

### Database Schema
See `database.md` for the planned database schema. The schema includes:
- Users
- Supplements (with inventory tracking)
- Stacks (supplement groupings)
- Stack supplements (junction table)
- Supplement logs (tracking)
- Schedules/reminders

## Key Implementation Notes

### Validation
- Domain entities validate their own invariants in factory methods and update methods
- Request DTOs use Jakarta Validation annotations (`@NotNull`, `@NotBlank`, etc.)
- Controllers accept `@Valid` request objects

### Exception Handling
- Use case-specific exceptions extend `RuntimeException`
- Global exception handler in `adapter/input/rest/GlobalExceptionHandler.java`
- Domain exceptions should be meaningful (e.g., `UserAlreadyExistsException`)

### Mappers
- DTOs map to domain objects via mapper classes
- Mappers live in `application/mapper/{feature}/`
- Keep mapping logic simple and explicit

### API Conventions
- Base path: `/api/v1/{resource}`
- Standard REST verbs: GET, POST, PUT, DELETE
- Return appropriate HTTP status codes (201 Created, 204 No Content, etc.)
- Use `userId` as query parameter for user-specific operations

### Testing Structure
```
src/test/java/com/BmoGlitchCode/supplemint/
├── domain/          # Unit tests for domain logic (no Spring)
├── application/     # Unit tests for use cases (mocked ports)
└── adapter/         # Integration tests (with Spring context)
```

## Future Considerations

- Replace in-memory repositories with JPA implementations
- Add database migration tooling (Flyway/Liquibase)
- Implement authentication/authorization (currently userId passed as parameter)
- Add logging capabilities
- Implement supplement tracking logs
- Add scheduling/reminder functionality
