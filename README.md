# 🌍 EduPlatform

<p align="center">
  <strong>Global Live-Learning Booking Platform for Teachers, Parents, and Students</strong>
</p>

<p align="center">
  Create learning batches, publish timezone-aware sessions, register parent accounts, and book seats safely without overbooking or schedule conflicts.
</p>

<p align="center">
  <a href="YOUR_DEPLOYMENT_URL">
    <img src="https://img.shields.io/badge/Live_Demo-Open_Platform-2ea44f?style=for-the-badge&logo=render" alt="Live Demo">
  </a>
  <a href="#-getting-started">
    <img src="https://img.shields.io/badge/Run_Locally-Get_Started-blue?style=for-the-badge&logo=docker" alt="Run Locally">
  </a>
  <a href="#-api-reference">
    <img src="https://img.shields.io/badge/API-Reference-orange?style=for-the-badge&logo=swagger" alt="API Reference">
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring_Boot-3.3.0-6DB33F?style=for-the-badge&logo=springboot" alt="Spring Boot 3.3.0">
  <img src="https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql" alt="PostgreSQL 16">
  <img src="https://img.shields.io/badge/Thymeleaf-UI-005F0F?style=for-the-badge&logo=thymeleaf" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/Flyway-Migrations-CC0200?style=for-the-badge&logo=flyway" alt="Flyway">
  <img src="https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker" alt="Docker">
</p>

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Live Deployment](#-live-deployment)
- [Demo Accounts](#-demo-accounts)
- [Platform Capabilities](#-platform-capabilities)
- [Technology Stack](#-technology-stack)
- [System Architecture](#-system-architecture)
- [Core Business Flows](#-core-business-flows)
- [Database Design and Concurrency](#-database-design-and-concurrency)
- [Timezone Strategy](#-timezone-strategy)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Using the Platform](#-using-the-platform)
- [API Reference](#-api-reference)
- [Validation and Error Handling](#-validation-and-error-handling)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Security Recommendations](#-security-recommendations)
- [Troubleshooting](#-troubleshooting)
- [Roadmap](#-roadmap)
- [Contributing](#-contributing)
- [License](#-license)

---

# 🚀 Overview

**EduPlatform** is a production-oriented live-learning booking application built with **Java 21**, **Spring Boot**, **PostgreSQL**, **Thymeleaf**, **Flyway**, and **Docker**.

The platform supports two primary user groups:

- **Teachers**, who create and manage live-learning course offerings.
- **Parents**, who register their own accounts, browse available batches, and reserve seats for their children.

The application is designed for global usage. Teachers may create sessions in their local timezone, while the platform converts and stores all session timestamps consistently in UTC.

EduPlatform also protects critical booking operations using application-level transactions, pessimistic database locking, unique constraints, and PostgreSQL exclusion constraints.

---

# 🌐 Live Deployment

<p align="center">
  <a href="YOUR_DEPLOYMENT_URL">
    <img src="https://img.shields.io/badge/Open_Live_EduPlatform-Visit_Now-success?style=for-the-badge&logo=googlechrome" alt="Open EduPlatform">
  </a>
</p>

| Resource | URL |
|---|---|
| Live Application | `YOUR_DEPLOYMENT_URL` |
| Home Page | `YOUR_DEPLOYMENT_URL/` |
| Login Page | `YOUR_DEPLOYMENT_URL/login` |
| Parent Registration | `YOUR_DEPLOYMENT_URL/register/parent` |
| Teacher Dashboard | `YOUR_DEPLOYMENT_URL/ui/teacher/dashboard` |
| Parent Dashboard | `YOUR_DEPLOYMENT_URL/ui/parent/dashboard` |
| Available Offerings | `YOUR_DEPLOYMENT_URL/offerings` |
| Parent Bookings | `YOUR_DEPLOYMENT_URL/bookings` |

> Replace every `YOUR_DEPLOYMENT_URL` value with the actual Render, Railway, AWS, Azure, Google Cloud, or other production URL.

---

# 🔐 Demo Accounts

The following accounts may be used to demonstrate the application after deployment.

## Teacher Demo Account

| Field | Value |
|---|---|
| Role | Teacher |
| Email | `teacher@eduplatform.com` |
| Password | `Teacher@123` |
| Access | Create and manage live-learning offerings |

## Parent Demo Account

| Field | Value |
|---|---|
| Role | Parent |
| Email | `parent@eduplatform.com` |
| Password | `Parent@123` |
| Access | Browse offerings and book seats |

> Demo credentials should be created through a Flyway seed migration or secure deployment seed process. Never publish real production administrator credentials in the repository.

---

# ✨ Platform Capabilities

## 👨‍🏫 Teacher Features

- Secure teacher login
- Teacher-specific dashboard
- Create live-learning batch offerings
- Select a course and teacher profile
- Set batch title and description
- Define maximum seat capacity
- Choose a valid IANA timezone
- Add one or more learning sessions
- View available and booked capacity
- Manage published offerings
- Review enrolled parents or students

## 👨‍👩‍👧 Parent Features

- Parent self-registration
- Secure parent login
- Parent-specific dashboard
- Browse available learning offerings
- View session dates and timings
- View teacher, course, timezone, and capacity details
- Book a seat for a child
- Prevent duplicate bookings
- Prevent overlapping class schedules
- Review confirmed bookings
- Manage parent profile information

## ⚙️ Platform Features

- Integrated Thymeleaf frontend
- Responsive glassmorphism-style interface
- RESTful JSON APIs
- Transaction-safe booking operations
- Pessimistic locking for capacity control
- Unique booking constraints
- PostgreSQL GiST exclusion constraints
- UTC-based global scheduling
- DTO-based API contracts
- MapStruct object mapping
- Jakarta Bean Validation
- Flyway database migrations
- Docker and Docker Compose support
- Environment-based configuration
- Production deployment compatibility

---

# 🛠️ Technology Stack

| Layer | Technology | Purpose |
|---|---|---|
| Language | Java 21 | Modern Java runtime, records, improved performance, and long-term support |
| Backend | Spring Boot 3.3.0 | Application bootstrap, dependency management, configuration, and production support |
| Web | Spring MVC | REST controllers and server-rendered web routes |
| UI | Thymeleaf, HTML5, CSS3, JavaScript | Integrated teacher and parent web interface |
| Persistence | Spring Data JPA | Repository abstraction and ORM integration |
| ORM | Hibernate | Entity mapping, transactions, and database persistence |
| Database | PostgreSQL 16 | Relational storage, constraints, locking, and range support |
| Migration | Flyway | Version-controlled database schema evolution |
| Mapping | MapStruct 1.5.5.Final | Compile-time entity and DTO mapping |
| Validation | Jakarta Bean Validation | Request and form validation |
| Build | Maven / Maven Wrapper | Build lifecycle and dependency management |
| Containerization | Docker | Portable application packaging |
| Orchestration | Docker Compose | Local application and database startup |
| Testing | JUnit 5, Spring Boot Test, Mockito | Unit, integration, and application testing |
| Deployment | Render, Railway, AWS, Azure, or equivalent | Public hosting and managed infrastructure |

---

# 🏗️ System Architecture

## High-Level Architecture

```mermaid
flowchart TB
    Browser[Teacher or Parent Browser]
    APIClient[REST API Client]
    Controller[Spring MVC Controllers]
    Service[Application Service Layer]
    Mapper[MapStruct DTO Mapping]
    Repository[Spring Data JPA Repositories]
    Locking[Pessimistic Booking Lock]
    Database[(PostgreSQL 16)]
    Flyway[Flyway Migrations]
    Templates[Thymeleaf Templates]

    Browser --> Controller
    APIClient --> Controller
    Controller --> Templates
    Controller --> Service
    Service --> Mapper
    Service --> Repository
    Repository --> Locking
    Locking --> Database
    Flyway --> Database
```

## Layered Architecture

```text
┌────────────────────────────────────────────────────────┐
│                  Presentation Layer                    │
│  Thymeleaf Pages • REST Controllers • Form Validation  │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│                  Application Layer                     │
│  Booking Services • Offering Services • Authentication │
│  Capacity Rules • Timezone Conversion • Transactions   │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│                   Persistence Layer                    │
│ Spring Data JPA • Hibernate • Pessimistic Write Locks  │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│                     Data Layer                         │
│ PostgreSQL • Unique Constraints • GiST Exclusion Rules │
│ UTC Session Storage • Flyway Schema History            │
└────────────────────────────────────────────────────────┘
```

## Main Domain Components

```mermaid
classDiagram
    class User {
        Long id
        String fullName
        String email
        String passwordHash
        Role role
        ZoneId timeZone
    }

    class Teacher {
        Long id
        String specialization
    }

    class Parent {
        Long id
        String phoneNumber
    }

    class Child {
        Long id
        String name
        Integer age
        String grade
    }

    class Course {
        Long id
        String title
        String description
    }

    class Offering {
        Long id
        String title
        Integer maxCapacity
        ZoneId timeZone
        OfferingStatus status
    }

    class Session {
        Long id
        Instant startUtc
        Instant endUtc
    }

    class Booking {
        Long id
        BookingStatus status
        Instant createdAt
    }

    User <|-- Teacher
    User <|-- Parent
    Parent "1" --> "0..*" Child
    Teacher "1" --> "0..*" Offering
    Course "1" --> "0..*" Offering
    Offering "1" --> "1..*" Session
    Parent "1" --> "0..*" Booking
    Offering "1" --> "0..*" Booking
```

---

# 🔄 Core Business Flows

## Parent Registration Flow

```mermaid
flowchart TD
    A[Open EduPlatform] --> B[Select Create Parent Account]
    B --> C[Enter Parent Details]
    C --> D[Enter Child Details if Required]
    D --> E[Choose Timezone]
    E --> F[Create Secure Password]
    F --> G{Validation Successful?}
    G -- No --> H[Show Validation Errors]
    H --> C
    G -- Yes --> I[Create Parent Account]
    I --> J[Redirect to Login]
    J --> K[Open Parent Dashboard]
```

## Teacher Offering Creation Flow

```mermaid
flowchart TD
    A[Teacher Login] --> B[Teacher Dashboard]
    B --> C[Create Offering]
    C --> D[Select Course]
    D --> E[Enter Capacity]
    E --> F[Choose Local Timezone]
    F --> G[Add Local Sessions]
    G --> H[Convert Sessions to UTC]
    H --> I[Validate Session Times]
    I --> J[Save Offering and Sessions]
    J --> K[Publish Offering]
```

## Safe Booking Flow

```mermaid
flowchart TD
    A[Parent Selects Offering] --> B[Begin Transaction]
    B --> C[Lock Offering Row]
    C --> D{Parent Already Booked?}
    D -- Yes --> E[Reject Duplicate Booking]
    D -- No --> F{Capacity Available?}
    F -- No --> G[Reject Full Offering]
    F -- Yes --> H{Schedule Overlap?}
    H -- Yes --> I[Reject Conflicting Schedule]
    H -- No --> J[Create Booking]
    J --> K[Create Parent Schedule Entries]
    K --> L[Commit Transaction]
    L --> M[Booking Confirmed]
```

---

# 🧠 Database Design and Concurrency

EduPlatform applies multiple protection layers because application-level checks alone are not sufficient under heavy concurrent traffic.

## 1. Pessimistic Write Locking

When a booking request is processed, the selected offering is loaded with a pessimistic write lock.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT o FROM Offering o WHERE o.id = :id")
Optional<Offering> findByIdForUpdate(@Param("id") Long id);
```

This produces database behavior similar to:

```sql
SELECT *
FROM offerings
WHERE id = ?
FOR UPDATE;
```

Only one booking transaction may update the locked offering at a time. This prevents two parents from reserving the final seat simultaneously.

## 2. Transactional Capacity Validation

The booking service should execute inside a transaction:

```java
@Transactional
public BookingResponse createBooking(CreateBookingRequest request) {
    Offering offering = offeringRepository.findByIdForUpdate(request.offeringId())
        .orElseThrow(() -> new ResourceNotFoundException("Offering not found"));

    long bookedSeats = bookingRepository.countConfirmedByOfferingId(offering.getId());

    if (bookedSeats >= offering.getMaxCapacity()) {
        throw new CapacityExceededException("No seats are available");
    }

    return saveBooking(request, offering);
}
```

## 3. Duplicate Booking Protection

A parent may book a particular offering only once.

```sql
ALTER TABLE bookings
ADD CONSTRAINT uk_booking_parent_offering
UNIQUE (parent_id, offering_id);
```

## 4. Parent Schedule Overlap Protection

PostgreSQL range types and GiST indexes prevent schedule collisions.

```sql
CREATE EXTENSION IF NOT EXISTS btree_gist;

ALTER TABLE parent_schedule
ADD CONSTRAINT no_parent_schedule_overlap
EXCLUDE USING gist (
    parent_id WITH =,
    time_range WITH &&
);
```

The `&&` operator returns true when two ranges overlap.

## 5. Why Both Application and Database Validation Are Used

| Protection | Responsibility |
|---|---|
| Service validation | Produces understandable business error messages |
| Transaction boundary | Keeps booking operations atomic |
| Pessimistic lock | Serializes capacity-sensitive booking requests |
| Unique constraint | Prevents duplicate booking records |
| Exclusion constraint | Prevents overlapping parent schedules |
| Database transaction | Rolls back partial booking changes |

---

# 🌍 Timezone Strategy

Teachers create sessions using a local date, local time, and IANA timezone.

Example input:

```json
{
  "timeZone": "Asia/Kolkata",
  "startLocal": "2026-08-01T10:00",
  "endLocal": "2026-08-01T12:00"
}
```

The application converts local session times to UTC before storage:

```java
ZoneId zoneId = ZoneId.of(request.timeZone());

Instant startUtc = request.startLocal()
    .atZone(zoneId)
    .toInstant();

Instant endUtc = request.endLocal()
    .atZone(zoneId)
    .toInstant();
```

Recommended PostgreSQL column type:

```sql
TIMESTAMP WITH TIME ZONE
```

Display conversion:

```text
Teacher local input
        ↓
IANA timezone validation
        ↓
ZonedDateTime
        ↓
UTC Instant
        ↓
PostgreSQL storage
        ↓
Convert to viewer timezone for display
```

This strategy helps avoid:

- Incorrect offsets
- Server timezone dependency
- Daylight-saving errors
- Region-to-region scheduling inconsistencies
- Ambiguous local timestamps

---

# 📁 Project Structure

```text
learning-platform/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/learningplatform/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       │   ├── api/
│   │   │       │   └── ui/
│   │   │       ├── dto/
│   │   │       │   ├── request/
│   │   │       │   └── response/
│   │   │       ├── entity/
│   │   │       ├── enums/
│   │   │       ├── exception/
│   │   │       ├── mapper/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       │   └── impl/
│   │   │       ├── util/
│   │   │       └── LearningPlatformApplication.java
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── images/
│   │       ├── templates/
│   │       │   ├── auth/
│   │       │   ├── parent/
│   │       │   ├── teacher/
│   │       │   ├── error/
│   │       │   └── index.html
│   │       ├── application.yml
│   │       └── application-prod.yml
│   └── test/
│       └── java/
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

# 🚀 Getting Started

## Prerequisites

### Docker-Based Setup

Install:

- Git
- Docker Desktop
- Docker Compose

### Local IDE Setup

Install:

- Java 21
- Git
- PostgreSQL 16 or Docker Desktop
- IntelliJ IDEA, Eclipse, or VS Code
- Maven 3.9+, or use the included Maven Wrapper

---

## Run with Docker Compose

### 1. Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
cd learning-platform
```

### 2. Create environment configuration

Create a `.env` file:

```env
POSTGRES_DB=learning_platform
POSTGRES_USER=learning_user
POSTGRES_PASSWORD=change_this_password

DB_HOST=postgres
DB_PORT=5432
DB_NAME=learning_platform
DB_USERNAME=learning_user
DB_PASSWORD=change_this_password

SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

### 3. Build and start the platform

```bash
docker compose up -d --build
```

### 4. Check container status

```bash
docker compose ps
```

### 5. Follow application logs

```bash
docker compose logs -f app
```

### 6. Open the application

```text
http://localhost:8080
```

### 7. Stop the application

```bash
docker compose down
```

### 8. Stop and remove database data

```bash
docker compose down -v
```

> The `-v` option permanently removes the local PostgreSQL volume.

---

## Run Locally from an IDE

### 1. Start PostgreSQL through Docker

```bash
docker compose up -d postgres
```

### 2. Run with Maven Wrapper

Linux or macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
mvnw.cmd spring-boot:run
```

### 3. Run from IntelliJ IDEA

Open:

```text
src/main/java/.../LearningPlatformApplication.java
```

Run the `main()` method.

---

# ⚙️ Configuration

## Environment Variables

| Variable | Required | Default | Description |
|---|---:|---|---|
| `DB_HOST` | Yes in production | `localhost` | PostgreSQL hostname |
| `DB_PORT` | No | `5432` | PostgreSQL port |
| `DB_NAME` | Yes | `learning_platform` | Database name |
| `DB_USERNAME` | Yes | `postgres` | Database username |
| `DB_PASSWORD` | Yes | `postgres` | Database password |
| `SERVER_PORT` | No | `8080` | Application port |
| `SPRING_PROFILES_ACTIVE` | No | `default` | Active Spring profile |
| `JAVA_OPTS` | No | Empty | JVM runtime options |
| `APP_BASE_URL` | Recommended | `http://localhost:8080` | Public application URL |

## Example `application.yml`

```yaml
spring:
  application:
    name: learning-platform

  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:learning_platform}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    open-in-view: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          time_zone: UTC

  flyway:
    enabled: true
    locations: classpath:db/migration

  jackson:
    serialization:
      write-dates-as-timestamps: false
    time-zone: UTC

  thymeleaf:
    cache: false
    check-template-location: true
    enabled: true
    mode: HTML

server:
  port: ${SERVER_PORT:8080}

app:
  base-url: ${APP_BASE_URL:http://localhost:8080}
```

---

# 🧑‍💻 Using the Platform

## Parent Registration

1. Open the EduPlatform home page.
2. Select **Create Parent Account**.
3. Enter the parent’s full name.
4. Enter a unique email address.
5. Enter a phone number.
6. Select a preferred timezone.
7. Enter child details when required.
8. Create and confirm a secure password.
9. Submit the registration form.
10. Log in with the newly created account.

Recommended registration route:

```text
/register/parent
```

Recommended API endpoint:

```http
POST /api/auth/parents/register
```

Example request:

```json
{
  "fullName": "Anita Sharma",
  "email": "anita.sharma@example.com",
  "phoneNumber": "9876543210",
  "password": "Parent@123",
  "confirmPassword": "Parent@123",
  "timeZone": "Asia/Kolkata",
  "children": [
    {
      "name": "Aarav Sharma",
      "age": 12,
      "grade": "Grade 7"
    }
  ]
}
```

## Teacher Usage

1. Log in with a teacher account.
2. Open the Teacher Dashboard.
3. Select **Create Offering**.
4. Choose a course.
5. Enter the offering title.
6. Set maximum seat capacity.
7. Select the teacher’s local timezone.
8. Add one or more sessions.
9. Review converted session details.
10. Publish the offering.

## Parent Booking Usage

1. Register or log in as a parent.
2. Open the Parent Dashboard.
3. Browse active offerings.
4. Review teacher, course, sessions, and available seats.
5. Select an offering.
6. Select a child when multiple children are registered.
7. Click **Book Seat**.
8. Review the confirmation.
9. View the booking under **My Bookings**.

---

# 📡 API Reference

> The exact routes should match the controller mappings in the source code. Update this section if your implementation uses different prefixes.

## Authentication and Registration

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/parents/register` | Register a new parent account |
| `POST` | `/api/auth/login` | Authenticate a user |
| `POST` | `/api/auth/logout` | End the active session |
| `GET` | `/api/auth/me` | Get the authenticated user |

### Register Parent

```http
POST /api/auth/parents/register
Content-Type: application/json
```

```json
{
  "fullName": "Anita Sharma",
  "email": "anita.sharma@example.com",
  "phoneNumber": "9876543210",
  "password": "Parent@123",
  "confirmPassword": "Parent@123",
  "timeZone": "Asia/Kolkata"
}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "parent@eduplatform.com",
  "password": "Parent@123"
}
```

---

## Offerings

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/offerings` | List available offerings |
| `GET` | `/offerings/{id}` | Get offering details |
| `POST` | `/offerings` | Create a new offering |
| `PUT` | `/offerings/{id}` | Update an offering |
| `DELETE` | `/offerings/{id}` | Remove or cancel an offering |

### Create Offering

```http
POST /offerings
Content-Type: application/json
```

```json
{
  "courseId": 1,
  "teacherId": 2,
  "title": "Summer Python Camp",
  "description": "Interactive Python fundamentals for school students.",
  "maxCapacity": 10,
  "timeZone": "Asia/Kolkata",
  "sessions": [
    {
      "startLocal": "2026-08-01T10:00",
      "endLocal": "2026-08-01T12:00"
    },
    {
      "startLocal": "2026-08-03T10:00",
      "endLocal": "2026-08-03T12:00"
    }
  ]
}
```

Example cURL:

```bash
curl -X POST "http://localhost:8080/offerings" \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 1,
    "teacherId": 2,
    "title": "Summer Python Camp",
    "description": "Interactive Python fundamentals for school students.",
    "maxCapacity": 10,
    "timeZone": "Asia/Kolkata",
    "sessions": [
      {
        "startLocal": "2026-08-01T10:00",
        "endLocal": "2026-08-01T12:00"
      }
    ]
  }'
```

---

## Bookings

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/bookings?parentId={id}` | List bookings for a parent |
| `GET` | `/bookings/{id}` | Get booking details |
| `POST` | `/bookings` | Reserve a seat |
| `DELETE` | `/bookings/{id}` | Cancel a booking |

### Create Booking

```http
POST /bookings
Content-Type: application/json
```

```json
{
  "parentId": 5,
  "childId": 8,
  "offeringId": 1
}
```

Example cURL:

```bash
curl -X POST "http://localhost:8080/bookings" \
  -H "Content-Type: application/json" \
  -d '{
    "parentId": 5,
    "childId": 8,
    "offeringId": 1
  }'
```

---

## Suggested HTTP Status Codes

| Status | Usage |
|---|---|
| `200 OK` | Successful retrieval or update |
| `201 Created` | Account, offering, or booking created |
| `204 No Content` | Successful deletion |
| `400 Bad Request` | Validation or malformed request |
| `401 Unauthorized` | Authentication required or failed |
| `403 Forbidden` | Authenticated user lacks permission |
| `404 Not Found` | Requested resource does not exist |
| `409 Conflict` | Duplicate booking, full offering, or schedule conflict |
| `422 Unprocessable Entity` | Valid JSON with invalid business data |
| `500 Internal Server Error` | Unexpected server failure |

---

# ✅ Validation and Error Handling

## Parent Registration Validation

- Full name is required
- Email must be valid
- Email must be unique
- Phone number must be valid
- Password must meet security requirements
- Password and confirmation must match
- Timezone must be a valid IANA timezone
- Child information must be valid when provided

## Offering Validation

- Course must exist
- Teacher must exist
- Title is required
- Maximum capacity must be greater than zero
- At least one session is required
- Session start must be before session end
- Timezone must be valid
- Duplicate or invalid sessions must be rejected

## Booking Validation

- Parent must exist
- Child must belong to the parent
- Offering must exist and be active
- Parent must not already have the same offering
- Offering must have available capacity
- Offering sessions must not overlap existing parent bookings

## Example Error Response

```json
{
  "timestamp": "2026-07-17T10:30:00Z",
  "status": 409,
  "error": "Conflict",
  "code": "SCHEDULE_CONFLICT",
  "message": "The selected class overlaps an existing booking.",
  "path": "/bookings"
}
```

## Recommended Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiError.of("RESOURCE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler({
        DuplicateBookingException.class,
        CapacityExceededException.class,
        ScheduleConflictException.class
    })
    ResponseEntity<ApiError> handleConflict(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiError.of("BOOKING_CONFLICT", ex.getMessage()));
    }
}
```

---

# 🧪 Testing

## Run All Tests

Linux or macOS:

```bash
./mvnw test
```

Windows:

```powershell
mvnw.cmd test
```

## Recommended Test Coverage

### Unit Tests

- Timezone conversion
- Capacity validation
- Duplicate booking detection
- Parent registration validation
- Password confirmation validation
- Entity-to-DTO mapping
- Service exception behavior

### Repository Tests

- Pessimistic lock query
- Booking count query
- Unique booking constraint
- Schedule exclusion constraint
- Offering session persistence

### Integration Tests

- Successful parent registration
- Duplicate email rejection
- Teacher login
- Parent login
- Offering creation
- Successful booking
- Full-capacity rejection
- Duplicate booking rejection
- Overlapping schedule rejection
- Transaction rollback behavior

### Concurrency Tests

A key integration test should create multiple simultaneous booking requests for the final available seat and verify that only one succeeds.

Expected result:

```text
Concurrent booking requests: 20
Available seats: 1
Successful bookings: 1
Rejected bookings: 19
Final confirmed booking count: 1
```

---

# 📦 Build Commands

## Clean and Compile

```bash
./mvnw clean compile
```

## Build JAR

```bash
./mvnw clean package
```

## Build Without Tests

```bash
./mvnw clean package -DskipTests
```

## Run Packaged Application

```bash
java -jar target/*.jar
```

## Build Docker Image

```bash
docker build -t eduplatform:latest .
```

## Run Docker Image

```bash
docker run --rm \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=5432 \
  -e DB_NAME=learning_platform \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  eduplatform:latest
```

---

# ☁️ Deployment

## Generic Production Deployment

A production deployment requires:

1. A managed PostgreSQL database
2. A Java or Docker-compatible web service
3. Environment variables
4. A public application URL
5. HTTPS
6. Persistent database storage
7. Health checks and logs

## Render Example

### Build Command

```bash
./mvnw clean package -DskipTests
```

### Start Command

```bash
java -Dserver.port=$PORT -jar target/*.jar
```

### Recommended Render Environment Variables

```env
DB_HOST=YOUR_RENDER_POSTGRES_HOST
DB_PORT=5432
DB_NAME=YOUR_RENDER_POSTGRES_DATABASE
DB_USERNAME=YOUR_RENDER_POSTGRES_USER
DB_PASSWORD=YOUR_RENDER_POSTGRES_PASSWORD
SPRING_PROFILES_ACTIVE=prod
APP_BASE_URL=https://YOUR_RENDER_SERVICE.onrender.com
JAVA_OPTS=-Xms256m -Xmx512m
```

For Render’s internal PostgreSQL connection, use the exact values shown in the database’s **Connections** section. Do not manually invent the host, database, username, or password.

## Docker Deployment

```bash
docker compose -f docker-compose.yml up -d --build
```

## Production Health Check

Recommended endpoint:

```text
/actuator/health
```

Example:

```json
{
  "status": "UP"
}
```

---

# 🔒 Security Recommendations

Before exposing EduPlatform publicly:

- Use Spring Security
- Hash passwords using BCrypt or Argon2
- Never store plain-text passwords
- Use secure session cookies
- Enable CSRF protection for server-rendered forms
- Apply role-based authorization
- Restrict teacher-only endpoints
- Restrict parent data to the authenticated parent
- Validate resource ownership
- Enforce HTTPS
- Store secrets only in environment variables
- Rotate demo passwords regularly
- Add login rate limiting
- Add account lockout protection
- Validate redirect URLs
- Sanitize user-controlled text
- Configure strict CORS rules
- Disable detailed stack traces in production
- Enable database backups
- Add audit logging for booking changes

Recommended access matrix:

| Capability | Teacher | Parent |
|---|---:|---:|
| Register parent account | No | Public registration |
| Create offering | Yes | No |
| Update own offering | Yes | No |
| Browse offerings | Yes | Yes |
| Book a seat | No | Yes |
| View own bookings | No | Yes |
| View enrollment for own offering | Yes | No |
| View another user’s private data | No | No |

---

# 📊 Observability Recommendations

For production readiness, consider adding:

- Spring Boot Actuator
- Health and readiness probes
- Structured JSON logging
- Request correlation IDs
- Database connection-pool metrics
- Booking success and rejection counters
- Capacity-exceeded metrics
- Schedule-conflict metrics
- Slow-query monitoring
- Error-rate alerts
- Uptime monitoring

Suggested metrics:

```text
eduplatform.bookings.created
eduplatform.bookings.rejected.capacity
eduplatform.bookings.rejected.duplicate
eduplatform.bookings.rejected.schedule_conflict
eduplatform.parents.registered
eduplatform.offerings.created
```

---

# 🛠️ Troubleshooting

<details>
<summary><strong>Application cannot connect to PostgreSQL</strong></summary>

Check container status:

```bash
docker compose ps
```

View PostgreSQL logs:

```bash
docker compose logs postgres
```

Confirm:

- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USERNAME`
- `DB_PASSWORD`

When the application runs inside Docker Compose, the database host is normally the service name:

```env
DB_HOST=postgres
```

When the application runs directly from an IDE, the database host is normally:

```env
DB_HOST=localhost
```

</details>

<details>
<summary><strong>Flyway migration failed</strong></summary>

View application logs:

```bash
docker compose logs app
```

Check migration history:

```sql
SELECT *
FROM flyway_schema_history
ORDER BY installed_rank;
```

Do not edit a migration that has already been applied in a shared database. Create a new versioned migration instead.

</details>

<details>
<summary><strong>Port 8080 is already in use</strong></summary>

Change the local Docker port mapping:

```yaml
ports:
  - "8081:8080"
```

Then open:

```text
http://localhost:8081
```

</details>

<details>
<summary><strong>PostgreSQL exclusion constraint cannot be created</strong></summary>

Enable the required extension:

```sql
CREATE EXTENSION IF NOT EXISTS btree_gist;
```

Place this statement in a Flyway migration before creating the exclusion constraint.

</details>

<details>
<summary><strong>Application sometimes shows “Not Found” after deployment</strong></summary>

Verify:

- The route exists in the controller
- The deployment service is fully started
- The platform is not sleeping or restarting
- The requested dashboard ID exists
- The public URL uses the correct path
- Reverse proxy health checks target a valid route
- Database migrations completed successfully
- The application binds to the deployment platform’s assigned port

For Render, use:

```bash
java -Dserver.port=$PORT -jar target/*.jar
```

</details>

<details>
<summary><strong>Database changes are not appearing</strong></summary>

Flyway versioned migrations run once.

Create a new migration:

```text
V2__add_parent_registration.sql
V3__add_child_table.sql
V4__add_booking_indexes.sql
```

For a disposable local database:

```bash
docker compose down -v
docker compose up -d --build
```

</details>

---

# 🗺️ Roadmap

- [ ] JWT-based API authentication
- [ ] Email verification
- [ ] Forgot-password flow
- [ ] Parent profile editing
- [ ] Multiple children per parent
- [ ] Teacher onboarding
- [ ] Administrator dashboard
- [ ] Course categories
- [ ] Search and filtering
- [ ] Waitlist management
- [ ] Booking cancellation policy
- [ ] Payment gateway integration
- [ ] Email and SMS notifications
- [ ] Calendar integration
- [ ] Live meeting links
- [ ] Attendance tracking
- [ ] Invoices and receipts
- [ ] Teacher analytics
- [ ] Parent notifications
- [ ] Redis caching
- [ ] OpenAPI and Swagger UI
- [ ] Spring Boot Actuator
- [ ] CI/CD pipeline
- [ ] Automated cloud backups
- [ ] Multi-language support

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository.
2. Create a feature branch.

```bash
git checkout -b feature/parent-registration
```

3. Make the required changes.
4. Add or update tests.
5. Commit the changes.

```bash
git commit -m "Add parent self-registration"
```

6. Push the branch.

```bash
git push origin feature/parent-registration
```

7. Open a pull request.

Please keep changes focused and include documentation for new endpoints, environment variables, migrations, and user-facing functionality.

---

# 📄 License

Add the selected license to the repository.

Example MIT notice:

```text
This project is licensed under the MIT License.
See the LICENSE file for details.
```

---

# 👨‍💻 Project Purpose

EduPlatform demonstrates how a Spring Boot application can solve real-world live-learning and booking challenges, including:

- Global timezone conversion
- Parent self-registration
- Role-specific user experiences
- Safe high-concurrency booking
- Transactional capacity enforcement
- Duplicate booking prevention
- Schedule overlap protection
- Database-level business rules
- Automated schema migrations
- Integrated frontend and backend delivery
- Containerized local and cloud deployment

---

<p align="center">
  <strong>🌍 Learn globally. Teach confidently. Book safely.</strong>
</p>

<p align="center">
  Built with Java 21, Spring Boot, PostgreSQL, Thymeleaf, Flyway, MapStruct, Maven, and Docker.
</p>

<p align="center">
  <a href="YOUR_DEPLOYMENT_URL">Live Application</a>
  ·
  <a href="YOUR_GITHUB_REPOSITORY_URL/issues">Report an Issue</a>
  ·
  <a href="YOUR_GITHUB_REPOSITORY_URL/pulls">Contribute</a>
</p>
