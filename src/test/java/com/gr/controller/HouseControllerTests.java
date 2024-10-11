package com.gr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.entity.House;
import com.gr.services.HouseService;
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
public class HouseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService houseService;

    private House house;

    @BeforeEach
    void setUp() {
        house = new House();
        house.setId(1L);
        house.setName("Test House");
        house.setAddress("123 Street, City");
    }

    @Test
    @WithMockUser(username = "testuser")
    void testListHouses() throws Exception {
        Mockito.when(houseService.listHouses()).thenReturn(Arrays.asList(house));

        mockMvc.perform(get("/api/house"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(house.getId()))
                .andExpect(jsonPath("$[0].name").value(house.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetHouse() throws Exception {
        Mockito.when(houseService.getHouseById(1L)).thenReturn(house);

        mockMvc.perform(get("/api/house/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(house.getId()))
                .andExpect(jsonPath("$.name").value(house.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateHouse() throws Exception {
        Mockito.when(houseService.createHouse(Mockito.any(House.class), Mockito.eq(1L))).thenReturn(house);

        mockMvc.perform(post("/api/house/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(house)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(house.getId()))
                .andExpect(jsonPath("$.name").value(house.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateHouse() throws Exception {
        Mockito.when(houseService.updateHouse(Mockito.eq(1L), Mockito.any(House.class))).thenReturn(house);

        mockMvc.perform(put("/api/house/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(house)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(house.getId()))
                .andExpect(jsonPath("$.name").value(house.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteHouse() throws Exception {
        Mockito.doNothing().when(houseService).deleteHouse(1L);

        mockMvc.perform(delete("/api/house/1"))
                .andExpect(status().isNoContent());
    }
}
