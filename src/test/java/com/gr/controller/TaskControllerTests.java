package com.gr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.entity.Task;
import com.gr.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setName("Sample Task");
    }

    @Test
    @WithMockUser(username = "testuser")
    void testListTasks() throws Exception {
        Mockito.when(taskService.listTasks()).thenReturn(Arrays.asList(task));

        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(task.getId()))
                .andExpect(jsonPath("$[0].name").value(task.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetTask() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateTask() throws Exception {
        Mockito.when(taskService.createTask(Mockito.any(Task.class), Mockito.eq(1L))).thenReturn(task);

        mockMvc.perform(post("/api/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateTask() throws Exception {
        Mockito.when(taskService.updateTask(Mockito.eq(1L), Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteTask() throws Exception {
        Mockito.doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/task/1"))
                .andExpect(status().isNoContent());
    }
}

