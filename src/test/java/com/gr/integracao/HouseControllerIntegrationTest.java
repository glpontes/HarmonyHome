package com.gr.integracao;

import org.junit.jupiter.api.*;
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
public class HouseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {}

    @Test
    @Order(1)
    public void testCreateHouse() throws Exception {
        String newHouse = "{\"name\":\"Casa teste\", \"address\":\"Endereco teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/house/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newHouse))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Casa teste\", \"address\":\"Endereco teste\"}"));
    }

    @Test
    public void testCreateHouseUserNotFound() throws Exception {
        String newHouse = "{\"name\":\"Casa teste\", \"address\":\"Endereco teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/house/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newHouse))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    public void testGetHouseById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/house/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Casa teste\", \"address\":\"Endereco teste\"}"));
    }

    @Test
    @Order(3)
    public void testListHouses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/house")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"Casa testada\", \"address\":null}]"));
    }

    @Test
    public void testNotFoundHouse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/house/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(3)
    public void testUpdateHouse() throws Exception {
        String updatedHouse = "{\"name\":\"Casa testada\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/house/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedHouse))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Casa testada\"}"));
    }

    @Test
    @Order(3)
    public void testUpdateHouseNotFound() throws Exception {
        String updatedHouse = "{\"name\":\"Casa testada\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/house/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedHouse))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteHouse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/house/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteHouseNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/house/100"))
                .andExpect(status().isNotFound());
    }

}
