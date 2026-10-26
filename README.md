# Task Assessment

Spring Boot REST API for managing tasks, backed by an in-memory H2 database.

## Backend

Requirements: JDK 17+, Maven (wrapper included).

Run:
```
./mvnw spring-boot:run
```
On Windows:
```
mvnw.cmd spring-boot:run
```

Server runs on http://localhost:9090. H2 console: http://localhost:9090/h2-console (JDBC URL `jdbc:h2:mem:task_assesment`, user `sa`).

API:
Base URL: `http://localhost:9090`

- POST /api/tasks
  - Description: Create a new task.
  - Request body (JSON):
    ```json
    {
      "title": "Buy milk",
      "description": "2 liters",
      "completed": false
    }
    ```
  - Success response: `201 Created`
    ```json
    {
      "id": 1,
      "title": "Buy milk",
      "description": "2 liters",
      "completed": false
    }
    ```
  - Validation error response: `400 Bad Request`
    ```json
    {
      "timestamp": "2026-01-01T00:00:00Z",
      "status": 400,
      "error": "Validation Failed",
      "errors": {
        "title": "title is required"
      }
    }
    ```

- GET /api/tasks
  - Description: Retrieve all tasks. Optional query param `completed=true|false` to filter by completion status.
  - Success response: `200 OK`
    ```json
    [
      { "id": 1, "title": "Buy milk", "description": "2 liters", "completed": false }
    ]
    ```

- GET /api/tasks?completed=true
  - Description: Retrieve tasks filtered by completion status.
  - Success response: `200 OK`
    ```json
    [
      { "id": 2, "title": "Done task", "description": "finished", "completed": true }
    ]
    ```

- GET /api/tasks/{id}
  - Description: Retrieve a single task by id.
  - Success response: `200 OK`
    ```json
    { "id": 1, "title": "Buy milk", "description": "2 liters", "completed": false }
    ```
  - Not found response: `404 Not Found`

## Frontend

Static UI served by Spring Boot at http://localhost:9090/ (index.html). No separate frontend build needed.

## Configuration

`src/main/resources/application.properties`:
```
spring.application.name=task_assesment
server.port = 9090

spring.datasource.url=jdbc:h2:mem:task_assesment
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.open-in-view=false

spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

Default: in-memory H2 (auto-configured). PostgreSQL schema: `db/schema.sql`.

## Tests

```
./mvnw test
```
