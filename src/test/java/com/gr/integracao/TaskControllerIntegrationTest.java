package com.gr.integracao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testuser")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){}


    @Test
    public void testListTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/task")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    public void testCreateTaskUserNotFound() throws Exception {
        String newTask = "{\"name\":\"Tarefa teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/task/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTask))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testNotFoundTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateTaskNotFound() throws Exception {
        String updatedTask = "{\"name\":\"Tarefa teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/task/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTask))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteTaskNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/100"))
                .andExpect(status().isNotFound());
    }

}
