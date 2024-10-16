package com.gr.integracao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testuser")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {}

    @Test
    public void testListRooms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/room")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    public void testCreateRoomUserNotFound() throws Exception {
        String newRoom = "{\"name\":\"Quarto teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/room/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRoom))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testNotFoundRoom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/room/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateRoomNotFound() throws Exception {
        String updatedRoom = "{\"name\":\"Quarto teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/room/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRoom))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteRoomNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/room/100"))
                .andExpect(status().isNotFound());
    }

}
