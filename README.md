# Task Management API

A simple REST API for managing tasks. I built this with Spring Boot while preparing for technical interviews — I wanted to have a complete project I could show and talk about.

## Tech stack

- Java 17
- Spring Boot 3.2.x
- Spring Data JPA
- JWT Authentication
- MySQL
- Maven
- JUnit 5

## What the API can do

| Method | Endpoint             | What it does                              |
|--------|----------------------|-------------------------------------------|
| POST   | `/api/auth/register` | Register a new user (returns JWT)         |
| POST   | `/api/auth/login`    | Login and get the JWT                     |
| POST   | `/api/tasks`         | Creates a task for the authenticated user |
| GET    | `/api/tasks`         | Get all tasks for the authenticated user  |
| PUT    | `/api/tasks/{id}`    | Updates a task for the authenticated user |
| DELETE | `/api/tasks/{id}`    | Deletes a task for the authenticated user |

It validates input data and handles errors with proper HTTP status codes (404, 400, 500).

## How to run it locally

**You'll need:**
- Java 17
- MySQL 8.0
- Maven

## Steps:

1. Clone the repo:
```bash
git clone https://github.com/NicolasMZF/Task-Management-API.git
cd Task-Management-API
```
2. Create the database:
```bash
CREATE DATABASE taskdb;
```
3. Update your database credentials in ```src/main/resources/application.yml```
4. Run it ```mvn spring-boot:run```

## Project Structure
```bash
src/
├── main/
│   ├── java/com/example/tasks/
│   │   ├── config/          # Security config
│   │   ├── controller/      # REST endpoints
│   │   ├── dto/             # DTO objects
│   │   ├── entity/          # Maps the object of the tables in the database 
│   │   ├── exception/       # Global exception handler
│   │   ├── repository/      # Handles the communication with the database
│   │   ├── security/        # Handles authentication and authorization logic
│   │   └── service/         # Validates business logic
│   └── resources/
│       └── application.yml  # Security and DB details
└── test/
    └── com/example/tasks/
        └── repository/
```
# What's included
- Full CRUD operations
- Input validation
- Unit tests with JUnit

# Runing the tests
```bash
mvn test
```

# About Me
I'm **Nicolas Steven Martinez**.
[Linkedin](https://www.linkedin.com/in/nicolas-steven-martinez-3b7490299) |
[GitHub](https://github.com/NicolasMZF)