package com.example.task_assesment.service;

import com.example.task_assesment.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getAllTasks();
    List<Task> getTasksByCompleted(boolean completed);
    Optional<Task> getTaskById(Long id);
}
