# EduPlatform: Global Live-Learning Booking API 🌍📚

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.0-6DB33F?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)

A robust, production-ready REST API for a global live-learning platform where **teachers can create batch offerings** and **parents can book seats** for their children. 

Built with Java 21 and Spring Boot 3.3.0, the backend features safe concurrency for booking limits, timezone-aware scheduling, and pessimistic locking to handle high demand scenarios.

---

## 🌟 Key Features

* **Beautiful Thymeleaf UI**: A modern, glassmorphism-styled UI for teachers and parents integrated directly into the Spring Boot app (no separate frontend required).
* **Atomic Capacity Bookings**: Uses Pessimistic Write locks to serialize booking requests and safely manage concurrent demand (preventing overbooking).
* **Schedule Collision Avoidance**: Uses PostgreSQL's `EXCLUDE USING gist` to strictly prevent a parent from double-booking themselves in conflicting time slots.
* **Global Timezones**: Allows teachers to define batches in their local timezone, automatically converting and saving sessions globally in UTC.
* **RESTful Architecture**: Clean, decoupled JSON APIs powered by Spring Boot.
* **MapStruct DTOs**: Optimised and secure data transfer layers.
* **Flyway Migrations**: Automated, reliable schema evolution.
* **Docker Ready**: One-command deployments via Docker Compose.

---

## 🛠️ Tech Stack

* **Backend Framework:** Spring Boot 3.3.0 (Web, Data JPA, Validation)
* **Language:** Java 21 
* **Database:** PostgreSQL 16
* **Database Migrations:** Flyway
* **Object Mapping:** MapStruct 1.5.5.Final
* **Containerization:** Docker & Docker Compose

---

## 🚀 Getting Started

The easiest way to spin up the entire application—including the PostgreSQL database and the API—is using Docker.

### Prerequisites

* [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed on your machine.

### Run with Docker Compose

1. **Clone the repository:**
   ```bash
   git clone <your-github-repo-url>
   cd learning-platform
   ```

2. **Start the environment:**
   ```bash
   docker-compose up -d --build
   ```

   This will:
   - Pull the PostgreSQL 16 image and start the database.
   - Build the Spring Boot application using a multi-stage Docker build.
   - Start the backend on `http://localhost:8080`.

3. **Check Logs:**
   ```bash
   docker-compose logs -f app
   ```

4. **Stop the environment:**
   ```bash
   docker-compose down
   ```

---

## 📖 Accessing the Platform

The server runs on `http://localhost:8080` by default.

### Portals

- **Home Page**: Navigate to [http://localhost:8080/](http://localhost:8080/)
- **Teacher Portal**: `http://localhost:8080/ui/teacher/{id}/dashboard`
- **Parent Portal**: `http://localhost:8080/ui/parent/{id}/dashboard`

*Note: For testing purposes, there is a Teacher with ID `1` and a Parent with ID `2` created via the Flyway migrations.*

---

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/offerings` | List all available course offerings. |
| `GET` | `/offerings/{id}` | Get details of a specific offering. |
| `POST` | `/offerings` | Create a new course offering. |

**Example `POST /offerings` Payload:**
```json
{
  "courseId": 1,
  "teacherId": 2,
  "title": "Summer Python Camp",
  "maxCapacity": 10,
  "timeZone": "Asia/Kolkata",
  "sessions": [
    {
      "startLocal": "2026-07-01T10:00",
      "endLocal": "2026-07-01T12:00"
    }
  ]
}
```

### 2. Bookings (Parents)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/bookings?parentId={id}` | List all bookings for a specific parent. |
| `POST` | `/bookings` | Book a seat in an offering for a parent. |

**Example `POST /bookings` Payload:**
```json
{
  "parentId": 5,
  "offeringId": 1
}
```

---

## 🧠 Database Constraints & Architecture

The application handles extreme concurrency using a mix of application-level locking and strict database-level constraints.

1. **Pessimistic Locking**: `SELECT ... FOR UPDATE` is used via `@Lock(LockModeType.PESSIMISTIC_WRITE)` when a parent tries to book an offering, preventing race conditions around capacity limits.
2. **Unique Constraints**: A parent can only book a specific offering once (`UNIQUE(parent_id, offering_id)`).
3. **No Overlap Constraint**: The `parent_schedule` table enforces an `EXCLUDE USING gist (parent_id WITH =, time_range WITH &&)` constraint to guarantee a parent never has overlapping classes.

---

## 🧑‍💻 Local Development (Without Docker for App)

If you wish to run the app directly via your IDE or Maven:

1. Start just the database:
   ```bash
   docker-compose up -d postgres
   ```
2. Build and run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

*(Requires Java 21 installed locally)*
