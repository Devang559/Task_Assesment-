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

API (base URL: `http://localhost:9090`):

1. Create Task
- Method: POST
- URL: `http://localhost:9090/api/tasks`
- Headers: `Content-Type: application/json`
- Body:
```json
{
  "title": "Buy milk",
  "description": "2 liters",
  "completed": false
}
```
- Response `201 Created`:
```json
{
  "id": 1,
  "title": "Buy milk",
  "description": "2 liters",
  "completed": false
}
```

2. Get All Tasks
- Method: GET
- URL: `http://localhost:9090/api/tasks`
- Response `200 OK`:
```json
[
  { "id": 1, "title": "Buy milk", "description": "2 liters", "completed": false }
]
```

3. Get Tasks By Completion Status
- Method: GET
- URL: `http://localhost:9090/api/tasks?completed=true`
- Response `200 OK`:
```json
[
  { "id": 2, "title": "Done task", "description": "finished", "completed": true }
]
```

4. Get Task By Id
- Method: GET
- URL: `http://localhost:9090/api/tasks/1`
- Response `200 OK`:
```json
{ "id": 1, "title": "Buy milk", "description": "2 liters", "completed": false }
```
- Response `404 Not Found` if task does not exist.

5. Validation Error (example)
- Method: POST
- URL: `http://localhost:9090/api/tasks`
- Body: `{}`
- Response `400 Bad Request`:
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
