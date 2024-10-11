package com.gr.integracao;

import com.gr.entity.House;
import com.gr.entity.User;
import com.gr.repository.HouseRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testuser")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    public void testGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"username\":\"admin\"}"));
    }

    @Test
    @Order(1)
    public void testListUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"name\":null, \"email\":null, \"username\":\"admin\"}]"));
    }

    @Test
    public void testNotFoundUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateUser() throws Exception {
        String newUser = "{\"name\":\"Jane Doe\", \"username\":\"jane\", \"password\":\"janepass\", \"email\":\"jane@dcx.ufpb.br\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUser))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"username\":\"jane\",\"name\":\"Jane Doe\",\"email\":\"jane@dcx.ufpb.br\"}"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        String updatedUser = "{\"name\":\"John Smith\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"John Smith\"}"));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        String updatedUser = "{\"name\":\"John Smith\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/100")
        .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUser))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/100"))
                .andExpect(status().isNotFound());
    }
}
