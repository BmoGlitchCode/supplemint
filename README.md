# Supplemint

A personal supplement tracker and management system built with Spring Boot 4.0 and Java 25, following Hexagonal Architecture principles.

## Features

- **User Management** - Register and authenticate users
- **Supplement Tracking** - Add, update, and manage your supplement inventory
- **Stack Management** - Group supplements into "stacks" for routines (e.g., Morning Stack, Pre-Workout)
- **Intake Logging** - Track when you take supplements with timestamps, notes, and skip tracking
- **Inventory Management** - Automatic inventory updates when logging intake

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 25 | Runtime |
| Spring Boot | 4.0.0 | Framework |
| Spring MVC | - | REST API |
| Jakarta Validation | - | Request validation |
| Lombok | - | Boilerplate reduction |
| SpringDoc OpenAPI | 2.7.0 | API documentation |
| Maven | - | Build tool |

## Architecture

This project follows **Hexagonal Architecture** (Ports and Adapters pattern) to maintain clean separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    REST Controllers                          │
│                    (Input Adapters)                          │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Input Ports                               │
│                (Use Case Interfaces)                         │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Application Layer                         │
│              (Use Case Implementations)                      │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Domain Layer                              │
│         (Entities, Value Objects, Business Logic)            │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Output Ports                              │
│               (Repository Interfaces)                        │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                In-Memory Repositories                        │
│                   (Output Adapters)                          │
└─────────────────────────────────────────────────────────────┘
```

**Key Principles:**
- Domain layer has zero framework dependencies
- Dependencies always point inward
- Business logic is isolated and testable

See [HEXAGONAL_ARCHITECTURE.md](HEXAGONAL_ARCHITECTURE.md) for detailed architecture documentation.

## Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.9+ (or use included Maven Wrapper)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/supplemint.git
cd supplemint
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`

### Test Data

On startup, the application initializes test data:

| Credential | Value |
|------------|-------|
| Email | `bmo@example.com` |
| Password | `password123` |

This creates a user with sample supplements, stacks, and log entries for testing.

## API Documentation

### Interactive Documentation

Once running, access the Swagger UI at:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

### Postman Collection

Import `supplemint_postman_collection.json` into Postman for a complete API collection with example requests.

### API Endpoints

#### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login user |

#### Supplements
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/supplements` | Create supplement |
| GET | `/api/v1/supplements` | List user's supplements |
| GET | `/api/v1/supplements/{id}` | Get supplement details |
| PUT | `/api/v1/supplements/{id}` | Update supplement |
| DELETE | `/api/v1/supplements/{id}` | Delete supplement |

#### Stacks
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/stacks` | Create stack |
| GET | `/api/v1/stacks` | List user's stacks |
| GET | `/api/v1/stacks/{id}` | Get stack details |
| PUT | `/api/v1/stacks/{id}` | Update stack |
| DELETE | `/api/v1/stacks/{id}` | Delete stack |
| POST | `/api/v1/stacks/{id}/items` | Add supplement to stack |
| DELETE | `/api/v1/stacks/{id}/items/{supplementId}` | Remove supplement from stack |

#### Supplement Logs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/supplement-logs` | Log supplement intake |
| GET | `/api/v1/supplement-logs` | List logs (with filters) |
| GET | `/api/v1/supplement-logs/{id}` | Get log details |
| PUT | `/api/v1/supplement-logs/{id}` | Update log entry |
| DELETE | `/api/v1/supplement-logs/{id}` | Delete log entry |

**Log Filters:**
- `supplementId` - Filter by supplement
- `stackId` - Filter by stack
- `startDate` / `endDate` - Filter by date range
- `skippedOnly` - Show only skipped doses

## Project Structure

```
src/main/java/com/BmoGlitchCode/supplemint/
├── domain/                          # Core business logic
│   ├── model/                       # Entities & value objects
│   │   ├── user/
│   │   ├── supplement/
│   │   ├── stack/
│   │   └── supplementlog/
│   └── port/
│       ├── input/                   # Use case interfaces
│       └── output/                  # Repository interfaces
│
├── application/                     # Application services
│   ├── usecase/                     # Use case implementations
│   ├── dto/
│   │   ├── request/                 # Input DTOs
│   │   └── response/                # Output DTOs
│   └── mapper/                      # DTO mappers
│
├── adapter/                         # External interfaces
│   ├── input/rest/                  # REST controllers
│   └── output/persistence/          # Repository implementations
│
└── infrastructure/                  # Configuration
    └── config/                      # Spring beans
```

## Testing

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=ClassName

# Run with coverage
./mvnw test jacoco:report
```

## Documentation

| File | Description |
|------|-------------|
| [HEXAGONAL_ARCHITECTURE.md](HEXAGONAL_ARCHITECTURE.md) | Architecture guide |
| [USER_STORIES.md](USER_STORIES.md) | User stories and API specs |
| [database.md](database.md) | Database schema design |
| [CLAUDE.md](CLAUDE.md) | AI assistant instructions |

## Roadmap

- [ ] Replace in-memory repositories with JPA/PostgreSQL
- [ ] Add database migrations (Flyway/Liquibase)
- [ ] Implement JWT authentication
- [ ] Add scheduling/reminder functionality
- [ ] Mobile app integration

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Built with care by [BmoGlitchCode](https://github.com/BmoGlitchCode)
