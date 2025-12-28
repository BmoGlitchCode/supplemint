// Personal Supplement Tracking App Database Schema
// Complete schema with simplified inventory tracking and flexible dosing

// ==================== USERS ====================
Table users {
id integer [primary key]
username varchar [unique, not null]
email varchar [unique, not null]
created_at timestamp [default: `now()`]
updated_at timestamp [default: `now()`]
}

// ==================== SUPPLEMENTS ====================
Table supplements {
id integer [primary key]
user_id integer [not null]
name varchar [not null]
description text
brand varchar [null]
default_dosage_amount decimal [null]
default_dosage_unit units [null]
notes text [null]

// Simplified Inventory Tracking
total_units decimal [not null] // total pills/scoops when bought
remaining_units decimal [not null] // currently remaining

is_active boolean [default: true]
created_at timestamp [default: `now()`]
updated_at timestamp [default: `now()`]

indexes {
user_id
(user_id, name)
}
}

// ==================== STACKS ====================
Table stacks {
id integer [primary key]
user_id integer [not null]
name varchar [not null]
description text [null]
default_time time_of_day [null]
color varchar [null] // for UI display
is_active boolean [default: true]
created_at timestamp [default: `now()`]
updated_at timestamp [default: `now()`]

indexes {
user_id
(user_id, is_active)
}
}

// ==================== STACK SUPPLEMENTS (Junction Table) ====================
Table stack_supplements {
id integer [primary key]
stack_id integer [not null]
supplement_id integer [not null]

// Specific dosage for this supplement in this stack
dosage_amount decimal [not null]
dosage_unit units [not null]
units_to_take decimal [default: 1] // how many capsules/scoops to take

sort_order integer [default: 0] // for display ordering
notes text [null]
created_at timestamp [default: `now()`]

indexes {
(stack_id, supplement_id) [unique]
supplement_id
}
}

// ==================== TRACKING LOGS ====================
Table supplement_logs {
id integer [primary key]
user_id integer [not null]
supplement_id integer [not null]
stack_id integer [null] // null if taken individually, otherwise part of stack
taken_at timestamp [not null]

// What they actually took
units_taken decimal [not null] // e.g., 2 capsules, 1.5 scoops
dosage_amount decimal [not null] // e.g., 5000
dosage_unit units [not null] // e.g., IU, mg

skipped boolean [default: false] // true if they skipped this dose
notes text [null] // side effects, how they felt, etc.
created_at timestamp [default: `now()`]

indexes {
(user_id, taken_at)
supplement_id
stack_id
(user_id, supplement_id, taken_at)
}
}

// ==================== SCHEDULES/REMINDERS ====================
Table schedules {
id integer [primary key]
user_id integer [not null]
stack_id integer [null] // schedule for entire stack
supplement_id integer [null] // or individual supplement

scheduled_time time [not null] // specific time like 08:00:00
time_of_day time_of_day [not null] // morning, afternoon, evening

// Days of week (bitmask: 1=Mon, 2=Tue, 4=Wed, 8=Thu, 16=Fri, 32=Sat, 64=Sun)
// Example: 31 = Mon-Fri (1+2+4+8+16), 127 = Every day
days_of_week integer [not null, default: 127]

reminder_enabled boolean [default: true]
is_active boolean [default: true]
created_at timestamp [default: `now()`]
updated_at timestamp [default: `now()`]

indexes {
user_id
(user_id, is_active)
}

Note: 'Either stack_id or supplement_id must be set, not both'
}

// ==================== ENUMS ====================
Enum units {
mg // milligrams
g // grams
mcg // micrograms
IU // International Units
ml // milliliters
}

Enum unit_types {
capsule
tablet
softgel
gummy
pill
scoop
drop
spray
ml
packet
sachet
}

Enum time_of_day {
morning
afternoon
evening
night
anytime
}

// ==================== RELATIONSHIPS ====================

// Users relationships
Ref: supplements.user_id > users.id [delete: cascade]
Ref: stacks.user_id > users.id [delete: cascade]
Ref: supplement_logs.user_id > users.id [delete: cascade]
Ref: schedules.user_id > users.id [delete: cascade]

// Supplements relationships
Ref: stack_supplements.supplement_id > supplements.id [delete: cascade]
Ref: supplement_logs.supplement_id > supplements.id [delete: restrict]
Ref: schedules.supplement_id > supplements.id [delete: cascade]

// Stacks relationships
Ref: stack_supplements.stack_id > stacks.id [delete: cascade]
Ref: supplement_logs.stack_id > stacks.id [delete: set null]
Ref: schedules.stack_id > stacks.id [delete: cascade]
