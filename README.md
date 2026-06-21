# Task Management API

A simple REST API for managing tasks. I built this with Spring Boot while preparing for technical interviews — I wanted to have a complete project I could show and talk about.

## Tech stack

- Java 17
- Spring Boot 3.2.x
- Spring Data JPA
- MySQL
- Maven
- JUnit 5 for testing

## What the API can do

| Method | Endpoint | What it does |
|--------|----------|--------------|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get one task by ID |
| PUT | `/api/tasks/{id}` | Update an existing task |
| DELETE | `/api/tasks/{id}` | Delete a task |

It validates input data and handles errors with proper HTTP status codes (404, 400, 500).

## How to run it locally

**You'll need:**
- Java 17
- MySQL 8.0
- Maven (or just run it from IntelliJ)

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
│   │   ├── entity/          # JPA entities
│   │   └── repository/      # JPA repositories
│   └── resources/
│       └── application.yml
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