# User Stories - Supplemint

This document maps out user stories based on the existing API endpoints and domain models.

---

## Domain Overview

### Core Entities

| Entity | Description |
|--------|-------------|
| **User** | Account holder who tracks supplements |
| **Supplement** | A single supplement product with dosage and inventory info |
| **Stack** | A named group of supplements taken together |
| **StackItem** | A supplement within a stack with sort order |

### Key Value Objects

| Value Object | Purpose |
|--------------|---------|
| `DosageType` | Form of supplement (PILL, GUMMY, SOFTGEL, SCOOP, DROP, SPRAY, etc.) |
| `DosageUnit` | Measurement unit (MG, G, MCG, ML, IU, etc.) |
| `TimeOfDay` | Suggested time (MORNING, AFTERNOON, EVENING, NIGHT, ANYTIME) |

---

## User Stories by Feature

### 1. User Authentication

#### US-1.1: Register Account
**As a** new user
**I want to** create an account with my email and password
**So that** I can start tracking my supplements

**Endpoint:** `POST /api/v1/auth/register`

**Request:**
```json
{
  "email": "user@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Acceptance Criteria:**
- Email must be valid format
- Password must be 8-128 characters
- First/last name max 50 characters each
- Duplicate emails are rejected

---

#### US-1.2: Login
**As a** registered user
**I want to** log in with my credentials
**So that** I can access my supplement data

**Endpoint:** `POST /api/v1/auth/login`

**Request:**
```json
{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Acceptance Criteria:**
- Invalid credentials return error
- Inactive accounts cannot log in
- Returns user profile on success

---

### 2. Supplement Management

#### US-2.1: Add New Supplement
**As a** user
**I want to** add a new supplement to my collection
**So that** I can track what I'm taking

**Endpoint:** `POST /api/v1/supplements`

**Request:**
```json
{
  "userId": "uuid",
  "name": "Vitamin D3",
  "description": "Supports bone health and immune function",
  "brand": "Nature Made",
  "dosageType": "SOFTGEL",
  "dosagePerServing": 125,
  "dosageUnit": "MCG",
  "servingSize": 1,
  "totalUnits": 180,
  "remainingUnits": 150,
  "notes": "Take with fatty meal for better absorption"
}
```

**Acceptance Criteria:**
- Name is required (max 100 chars)
- Description max 500 chars
- Brand max 100 chars
- Dosage amount must be positive
- Serving size must be positive
- Total units must be positive
- Remaining units is optional (defaults to total units if not provided)
- Remaining units cannot be negative
- Remaining units cannot exceed total units

---

#### US-2.2: View My Supplements
**As a** user
**I want to** see all my supplements
**So that** I know what I have in my inventory

**Endpoint:** `GET /api/v1/supplements?userId={uuid}&activeOnly={boolean}`

**Query Parameters:**
| Parameter | Required | Default | Description |
|-----------|----------|---------|-------------|
| userId | Yes | - | User's UUID |
| activeOnly | No | true | Filter archived supplements |

**Acceptance Criteria:**
- Returns only supplements belonging to the user
- Can filter to show only active supplements
- List includes inventory counts

---

#### US-2.3: View Supplement Details
**As a** user
**I want to** view details of a specific supplement
**So that** I can see its full information

**Endpoint:** `GET /api/v1/supplements/{id}?userId={uuid}`

**Acceptance Criteria:**
- Returns 404 if supplement not found
- Returns 403 if supplement belongs to another user
- Shows all supplement properties including inventory

---

#### US-2.4: Update Supplement
**As a** user
**I want to** update supplement information
**So that** I can correct details or track inventory changes

**Endpoint:** `PUT /api/v1/supplements/{id}`

**Request:**
```json
{
  "userId": "uuid",
  "name": "Vitamin D3 5000 IU",
  "description": "Updated description",
  "brand": "Nature Made",
  "dosageType": "SOFTGEL",
  "dosagePerServing": 125,
  "dosageUnit": "MCG",
  "servingSize": 1,
  "notes": "Updated notes",
  "remainingUnits": 150
}
```

**Acceptance Criteria:**
- Can update name, description, brand, notes
- Can update dosage configuration
- Can update remaining inventory count
- Remaining units cannot be negative
- Returns 403 if not owner

---

#### US-2.5: Delete/Archive Supplement
**As a** user
**I want to** delete a supplement I no longer take
**So that** it doesn't clutter my active list

**Endpoint:** `DELETE /api/v1/supplements/{id}?userId={uuid}`

**Acceptance Criteria:**
- Returns 204 No Content on success
- Returns 403 if not owner
- Returns 404 if not found
- Supplement is soft-deleted (marked inactive)

---

### 3. Stack Management

#### US-3.1: Create Supplement Stack
**As a** user
**I want to** create a stack of supplements
**So that** I can group supplements I take together

**Endpoint:** `POST /api/v1/stacks`

**Request:**
```json
{
  "userId": "uuid",
  "name": "Morning Routine",
  "description": "Supplements I take every morning with breakfast",
  "defaultTime": "MORNING",
  "color": "#4CAF50",
  "items": [
    { "supplementId": "uuid" },
    { "supplementId": "uuid", "sortOrder": 1 }
  ]
}
```

**Acceptance Criteria:**
- Name is required (max 100 chars)
- Description max 500 chars
- Color must be valid hex format (#RGB or #RRGGBB)
- Items are optional on creation
- Sort order is optional (auto-assigned based on insertion order if not provided)
- Default time can be: MORNING, AFTERNOON, EVENING, NIGHT, ANYTIME

---

#### US-3.2: View My Stacks
**As a** user
**I want to** see all my supplement stacks
**So that** I can manage my routines

**Endpoint:** `GET /api/v1/stacks?userId={uuid}&activeOnly={boolean}`

**Query Parameters:**
| Parameter | Required | Default | Description |
|-----------|----------|---------|-------------|
| userId | Yes | - | User's UUID |
| activeOnly | No | false | Filter archived stacks |

**Acceptance Criteria:**
- Returns all stacks for the user
- Each stack includes its items with supplement details
- Can filter to active-only

---

#### US-3.3: View Stack Details
**As a** user
**I want to** view details of a specific stack
**So that** I can see all supplements in that routine

**Endpoint:** `GET /api/v1/stacks/{stackId}?userId={uuid}`

**Response includes:**
- Stack metadata (name, description, time, color)
- All stack items with full supplement details
- Sort order for display

**Acceptance Criteria:**
- Returns 404 if stack not found
- Returns 403 if stack belongs to another user
- Includes full supplement information for each item

---

#### US-3.4: Update Stack
**As a** user
**I want to** update a stack's information and contents
**So that** I can modify my supplement routines

**Endpoint:** `PUT /api/v1/stacks/{stackId}`

**Request:**
```json
{
  "userId": "uuid",
  "name": "Updated Morning Routine",
  "description": "New description",
  "defaultTime": "MORNING",
  "color": "#2196F3",
  "items": [
    { "supplementId": "uuid", "sortOrder": 0 }
  ]
}
```

**Acceptance Criteria:**
- Can update name, description, time, color
- Items list completely replaces existing items
- Sort order is optional (auto-assigned based on insertion order if not provided)
- Returns 403 if not owner

---

#### US-3.5: Delete Stack
**As a** user
**I want to** delete a stack I no longer use
**So that** it doesn't clutter my routines list

**Endpoint:** `DELETE /api/v1/stacks/{stackId}?userId={uuid}`

**Acceptance Criteria:**
- Returns 204 No Content on success
- Returns 403 if not owner
- Returns 404 if not found
- Does NOT delete the supplements themselves

---

#### US-3.6: Add Supplement to Stack
**As a** user
**I want to** add a supplement to an existing stack
**So that** I can build up my routine incrementally

**Endpoint:** `POST /api/v1/stacks/{stackId}/items`

**Request:**
```json
{
  "userId": "uuid",
  "supplementId": "uuid",
  "sortOrder": 2
}
```

**Acceptance Criteria:**
- Sort order is optional (defaults to 0 if not provided)
- Sort order cannot be negative
- Cannot add duplicate supplement to same stack
- Returns 403 if not owner of stack

---

#### US-3.7: Remove Supplement from Stack
**As a** user
**I want to** remove a supplement from a stack
**So that** I can adjust my routine

**Endpoint:** `DELETE /api/v1/stacks/{stackId}/items/{supplementId}?userId={uuid}`

**Acceptance Criteria:**
- Returns 204 No Content on success
- Returns 403 if not owner
- Does NOT delete the supplement itself, only removes from stack

---

## Data Relationships

```
User (1) ----< (N) Supplement
  |
  +----------< (N) Stack (1) ----< (N) StackItem >---- (1) Supplement
```

- A **User** owns many **Supplements**
- A **User** owns many **Stacks**
- A **Stack** contains many **StackItems**
- Each **StackItem** references one **Supplement**
- A **Supplement** can appear in multiple **Stacks** (via StackItems)

---

## Validation Rules Summary

### User
| Field | Constraints |
|-------|-------------|
| email | Required, valid email format, max 255 chars, unique |
| password | Required, 8-128 chars |
| firstName | Optional, max 50 chars |
| lastName | Optional, max 50 chars |

### Supplement
| Field | Constraints |
|-------|-------------|
| name | Required, max 100 chars |
| description | Optional, max 500 chars |
| brand | Optional, max 100 chars |
| dosageType | Enum: SOFTGEL, PILL, GUMMY, LOZENGE, CHEWABLE, SCOOP, DROP, SPRAY, SERVING |
| dosagePerServing | Positive number |
| dosageUnit | Enum: MG, G, MCG, KG, ML, L, FL_OZ, TSP, TBSP, IU |
| servingSize | Positive number |
| totalUnits | Positive number |
| remainingUnits | Optional on create (defaults to totalUnits), non-negative, cannot exceed totalUnits |
| notes | Optional, max 1000 chars |

### Stack
| Field | Constraints |
|-------|-------------|
| name | Required, max 100 chars |
| description | Optional, max 500 chars |
| defaultTime | Enum: MORNING, AFTERNOON, EVENING, NIGHT, ANYTIME |
| color | Optional, valid hex (#RGB or #RRGGBB), max 7 chars |

### StackItem
| Field | Constraints |
|-------|-------------|
| supplementId | Required, must exist |
| sortOrder | Optional, non-negative integer (auto-assigned if not provided) |

---

## Error Responses

| HTTP Status | Error Code | Description |
|-------------|------------|-------------|
| 400 | VALIDATION_ERROR | Request validation failed |
| 403 | SUPPLEMENT_ACCESS_DENIED | User doesn't own the supplement |
| 403 | STACK_ACCESS_DENIED | User doesn't own the stack |
| 404 | SUPPLEMENT_NOT_FOUND | Supplement doesn't exist |
| 404 | STACK_NOT_FOUND | Stack doesn't exist |
| 409 | USER_ALREADY_EXISTS | Email already registered |
| 401 | INVALID_CREDENTIALS | Login failed |

---

## Future Considerations

Based on the database schema (`database.md`), these features are planned:

- **Supplement Logging**: Track daily supplement intake
- **Schedules/Reminders**: Set up automated reminders for stacks
- **Authentication**: Replace userId param with proper session-based auth
