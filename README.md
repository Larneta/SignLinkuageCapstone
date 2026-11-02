# SignLinkuage Capstone

SignLinkuage is a Spring Boot REST API for an American Sign Language (ASL) learning platform. It provides authenticated access to learning modules and lessons, quizzes, exams, and learner progress.

## Features

- User registration and JWT-based login
- ASL modules and lessons with learner progress tracking
- Quiz content and module-level exams with automatic exam scoring
- Admin-only content management
- MySQL persistence through Spring Data JPA and Hibernate

## Technology

- Java 17
- Spring Boot 3.5.0
- Spring Web, Spring Security, Spring Data JPA, and Bean Validation
- MySQL
- Maven
- JSON Web Tokens (JWT)

This repository contains the backend API; a frontend application is not included.

## Prerequisites

- JDK 17 or newer
- MySQL 8 or compatible
- Git

Maven is not required separately when using the included Maven Wrapper.

## Run locally

1. Clone the repository and enter its directory:

   ```powershell
   git clone <repository-url>
   cd SignLinkuageCapstone
   ```

2. Create the database:

   ```sql
   CREATE DATABASE signlinkuage;
   ```

3. Configure the database connection and JWT secret. The defaults are in `src/main/resources/application.properties`; override them with environment variables rather than committing credentials:

   ```powershell
   $env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/signlinkuage?useSSL=false&serverTimezone=UTC"
   $env:SPRING_DATASOURCE_USERNAME = "root"
   $env:SPRING_DATASOURCE_PASSWORD = "<your-mysql-password>"
   $env:JWT_SECRET = "<a-private-random-secret-of-at-least-256-bits>"
   ```

   If your local MySQL account has no password, set `SPRING_DATASOURCE_PASSWORD` to an empty string. Use a unique secret for `JWT_SECRET`, and do not commit it.

4. Start the API:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   The server listens on `http://localhost:8081` by default. Hibernate is configured to update the schema on startup.

To compile or run tests:

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd test
```

## Authentication

Registration and login are public. Other API routes require a JWT, and `/api/admin/**` routes require an administrator role.

Register with `POST /api/auth/register`, then log in with `POST /api/auth/login`:

```http
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "email": "learner@example.com",
  "password": "your-password"
}
```

Include the returned token on protected requests:

```http
Authorization: Bearer <token>
```

## API overview

All routes are under `http://localhost:8081`. Protected routes require authentication unless noted.

| Area | Method | Route | Access |
|---|---|---|---|
| Authentication | `POST` | `/api/auth/register` | Public |
| Authentication | `POST` | `/api/auth/login` | Public |
| Authentication | `GET` | `/api/auth/me` | Authenticated |
| Modules | `GET` | `/api/modules` | Authenticated |
| Modules | `GET` | `/api/modules/{moduleId}` | Authenticated |
| Modules | `POST`, `PUT`, `DELETE` | `/api/modules[/{moduleId}]` | Admin |
| Lessons | `GET` | `/api/lessons/{lessonId}` | Authenticated |
| Lessons | `GET` | `/api/lessons/module/{moduleId}` | Authenticated |
| Lessons | `POST`, `PUT`, `DELETE` | `/api/lessons[/{lessonId}]` | Admin |
| Lessons | `POST` | `/api/lessons/{lessonId}/complete` | Authenticated |
| Quizzes | `GET` | `/api/quizzes/module/{moduleId}` | Authenticated |
| Quizzes | `GET` | `/api/quizzes/{quizId}` | Authenticated |
| Quizzes | `GET` | `/api/quizzes/{quizId}/questions` | Authenticated |
| Quizzes | `GET` | `/api/quizzes/questions/{questionId}/answers` | Authenticated |
| Quizzes | `POST`, `DELETE` | `/api/quizzes[/{quizId}]` | Admin |
| Exams | `GET` | `/api/exams/{examId}` | Authenticated |
| Exams | `GET` | `/api/exams/module/{moduleId}` | Authenticated |
| Exams | `POST`, `PUT`, `DELETE` | `/api/exams[/{examId}]` | Authenticated |
| Exams | `POST` | `/api/exams/submit` | Authenticated |
| Dashboard | `GET` | `/api/dashboard` | Authenticated |

Admin module and lesson creation are also available at `POST /api/admin/modules` and `POST /api/admin/lessons`; quiz creation and deletion are also available under `/api/admin/quizzes`.

## Sample exam data

`src/main/resources/exam_data.sql` contains optional exam sample data. Create the database and ensure the referenced module exists before loading it. For example:

```powershell
Get-Content -Raw src\main\resources\exam_data.sql | mysql -u root -p signlinkuage
```

## Project layout

```text
src/main/java/org/example/signlinkuagecapstone/
  controller/    REST endpoints
  dto/           API request and response types
  entity/        JPA entities
  repository/    Database access
  security/      JWT and security configuration
  service/       Application and business logic
src/main/resources/
  application.properties
  exam_data.sql
postman/
  SignLinkuageCapstone.postman_collection.json
```

Additional exam documentation is available in `EXAM_QUICKSTART.md`, `EXAM_ARCHITECTURE.md`, and `EXAM_SYSTEM_DOCUMENTATION.md`.
