package com.example.task_assesment.controller;

import com.example.task_assesment.entity.Task;
import com.example.task_assesment.service.TaskService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_returnsCreated() throws Exception {
        Task task = new Task("Buy milk", "2 liters", false);
        task.setId(1L);
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task("Buy milk", "2 liters", false))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Buy milk"));
    }

    @Test
    void createTask_validationError_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTasks_returnsOk() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of(new Task("T1", "d", false)));
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getTasksByCompleted_filters() throws Exception {
        when(taskService.getTasksByCompleted(true)).thenReturn(List.of(new Task("Done", "d", true)));
        mockMvc.perform(get("/api/tasks").param("completed", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    @Test
    void getTaskById_notFound() throws Exception {
        when(taskService.getTaskById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());
    }
}
