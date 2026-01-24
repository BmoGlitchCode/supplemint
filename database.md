# Database Schema

This document describes the database schema for Supplemint, a personal supplement tracking application.

## Entity Relationship Diagram

```
┌─────────────┐       ┌─────────────────┐       ┌─────────────┐
│   users     │       │   supplements   │       │   stacks    │
├─────────────┤       ├─────────────────┤       ├─────────────┤
│ id (PK)     │◄──┬───│ user_id (FK)    │   ┌───│ user_id (FK)│───►│
│ username    │   │   │ id (PK)         │   │   │ id (PK)     │    │
│ email       │   │   │ name            │   │   │ name        │    │
│ ...         │   │   │ ...             │   │   │ ...         │    │
└─────────────┘   │   └────────┬────────┘   │   └──────┬──────┘    │
                  │            │            │          │           │
                  │            │            │          │           │
                  │            ▼            │          ▼           │
                  │   ┌─────────────────────┴──────────────────┐   │
                  │   │         stack_supplements              │   │
                  │   ├────────────────────────────────────────┤   │
                  │   │ id (PK)                                │   │
                  │   │ stack_id (FK) ─────────────────────────┘   │
                  │   │ supplement_id (FK)                         │
                  │   │ sort_order                                 │
                  │   └────────────────────────────────────────────┘
                  │
                  │   ┌────────────────────┐
                  │   │  supplement_logs   │
                  │   ├────────────────────┤
                  ├───│ user_id (FK)       │
                  │   │ supplement_id (FK) │───► supplements
                  │   │ stack_id (FK)      │───► stacks (nullable)
                  │   │ taken_at           │
                  │   │ units_taken        │
                  │   │ skipped            │
                  │   └────────────────────┘
                  │
                  │   ┌────────────────────┐
                  │   │     schedules      │  (Future)
                  │   ├────────────────────┤
                  └───│ user_id (FK)       │
                      │ supplement_id (FK) │───► supplements (nullable)
                      │ stack_id (FK)      │───► stacks (nullable)
                      │ scheduled_time     │
                      │ days_of_week       │
                      └────────────────────┘
```

---

## Tables

### users

Stores user account information.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY | Unique identifier |
| `username` | VARCHAR | UNIQUE, NOT NULL | Display name |
| `email` | VARCHAR | UNIQUE, NOT NULL | Login email |
| `created_at` | TIMESTAMP | DEFAULT now() | Account creation time |
| `updated_at` | TIMESTAMP | DEFAULT now() | Last update time |

---

### supplements

Individual supplement products owned by a user.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY | Unique identifier |
| `user_id` | INTEGER | NOT NULL, FK → users | Owner reference |
| `name` | VARCHAR | NOT NULL | Supplement name |
| `description` | TEXT | NULL | Optional description |
| `brand` | VARCHAR | NULL | Manufacturer/brand |
| `default_dosage_amount` | DECIMAL | NULL | Default dose amount |
| `default_dosage_unit` | ENUM | NULL | Unit of measurement |
| `notes` | TEXT | NULL | User notes |
| `total_units` | DECIMAL | NOT NULL | Total units when purchased |
| `remaining_units` | DECIMAL | NOT NULL | Current inventory count |
| `is_active` | BOOLEAN | DEFAULT true | Soft delete flag |
| `created_at` | TIMESTAMP | DEFAULT now() | Creation time |
| `updated_at` | TIMESTAMP | DEFAULT now() | Last update time |

**Indexes:**
- `user_id`
- `(user_id, name)`

---

### stacks

Named groups of supplements taken together (e.g., "Morning Routine").

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY | Unique identifier |
| `user_id` | INTEGER | NOT NULL, FK → users | Owner reference |
| `name` | VARCHAR | NOT NULL | Stack name |
| `description` | TEXT | NULL | Optional description |
| `default_time` | ENUM | NULL | Suggested time of day |
| `color` | VARCHAR | NULL | UI display color (hex) |
| `is_active` | BOOLEAN | DEFAULT true | Soft delete flag |
| `created_at` | TIMESTAMP | DEFAULT now() | Creation time |
| `updated_at` | TIMESTAMP | DEFAULT now() | Last update time |

**Indexes:**
- `user_id`
- `(user_id, is_active)`

---

### stack_supplements

Junction table linking supplements to stacks (many-to-many).

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY | Unique identifier |
| `stack_id` | INTEGER | NOT NULL, FK → stacks | Stack reference |
| `supplement_id` | INTEGER | NOT NULL, FK → supplements | Supplement reference |
| `sort_order` | INTEGER | DEFAULT 0 | Display order within stack |
| `notes` | TEXT | NULL | Stack-specific notes |
| `created_at` | TIMESTAMP | DEFAULT now() | Creation time |

**Indexes:**
- `(stack_id, supplement_id)` UNIQUE
- `supplement_id`

---

### supplement_logs

Records of supplement intake or skipped doses.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY | Unique identifier |
| `user_id` | INTEGER | NOT NULL, FK → users | User reference |
| `supplement_id` | INTEGER | NOT NULL, FK → supplements | Supplement taken |
| `stack_id` | INTEGER | NULL, FK → stacks | Associated stack (if any) |
| `taken_at` | TIMESTAMP | NOT NULL | When taken/scheduled |
| `units_taken` | DECIMAL | NOT NULL | Number of units (pills, scoops) |
| `dosage_amount` | DECIMAL | NOT NULL | Actual dosage amount |
| `dosage_unit` | ENUM | NOT NULL | Unit of measurement |
| `skipped` | BOOLEAN | DEFAULT false | True if dose was skipped |
| `notes` | TEXT | NULL | Side effects, feelings, etc. |
| `created_at` | TIMESTAMP | DEFAULT now() | Record creation time |

**Indexes:**
- `(user_id, taken_at)`
- `supplement_id`
- `stack_id`
- `(user_id, supplement_id, taken_at)`

---

### schedules (Future)

Scheduled reminders for supplements or stacks.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY | Unique identifier |
| `user_id` | INTEGER | NOT NULL, FK → users | User reference |
| `stack_id` | INTEGER | NULL, FK → stacks | Schedule for stack |
| `supplement_id` | INTEGER | NULL, FK → supplements | Schedule for supplement |
| `scheduled_time` | TIME | NOT NULL | Time of day (e.g., 08:00:00) |
| `time_of_day` | ENUM | NOT NULL | General time period |
| `days_of_week` | INTEGER | NOT NULL, DEFAULT 127 | Bitmask for days |
| `reminder_enabled` | BOOLEAN | DEFAULT true | Send notifications |
| `is_active` | BOOLEAN | DEFAULT true | Soft delete flag |
| `created_at` | TIMESTAMP | DEFAULT now() | Creation time |
| `updated_at` | TIMESTAMP | DEFAULT now() | Last update time |

> **Note:** Either `stack_id` or `supplement_id` must be set, not both.

**Days of Week Bitmask:**
| Day | Value | Example |
|-----|-------|---------|
| Monday | 1 | |
| Tuesday | 2 | |
| Wednesday | 4 | |
| Thursday | 8 | |
| Friday | 16 | |
| Saturday | 32 | |
| Sunday | 64 | |
| Mon-Fri | 31 | 1+2+4+8+16 |
| Every day | 127 | All bits set |

**Indexes:**
- `user_id`
- `(user_id, is_active)`

---

## Enums

### units (Dosage Units)

| Value | Description |
|-------|-------------|
| `mg` | Milligrams |
| `g` | Grams |
| `mcg` | Micrograms |
| `IU` | International Units |
| `ml` | Milliliters |

### unit_types (Supplement Forms)

| Value | Description |
|-------|-------------|
| `capsule` | Hard-shell capsule |
| `tablet` | Compressed tablet |
| `softgel` | Soft gelatin capsule |
| `gummy` | Chewable gummy |
| `pill` | Generic pill |
| `scoop` | Powder scoop |
| `drop` | Liquid drop |
| `spray` | Oral spray |
| `ml` | Milliliter serving |
| `packet` | Single-serve packet |
| `sachet` | Powder sachet |

### time_of_day

| Value | Description |
|-------|-------------|
| `morning` | Morning (6am - 12pm) |
| `afternoon` | Afternoon (12pm - 5pm) |
| `evening` | Evening (5pm - 9pm) |
| `night` | Night (9pm - 6am) |
| `anytime` | No specific time |

---

## Relationships & Cascade Rules

| Parent | Child | On Delete |
|--------|-------|-----------|
| users | supplements | CASCADE |
| users | stacks | CASCADE |
| users | supplement_logs | CASCADE |
| users | schedules | CASCADE |
| supplements | stack_supplements | CASCADE |
| supplements | supplement_logs | RESTRICT |
| supplements | schedules | CASCADE |
| stacks | stack_supplements | CASCADE |
| stacks | supplement_logs | SET NULL |
| stacks | schedules | CASCADE |

**Key Behaviors:**
- Deleting a user removes all their data
- Deleting a supplement is blocked if logs exist (preserves history)
- Deleting a stack removes it from logs but keeps the log records
