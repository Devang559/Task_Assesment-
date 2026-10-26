-- PostgreSQL schema for tasks
CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    completed BOOLEAN NOT NULL DEFAULT FALSE
);
