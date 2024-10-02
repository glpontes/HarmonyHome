package com.gr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.dto.RoomDTO;
import com.gr.entity.Room;
import com.gr.services.RoomService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ModelMapper modelMapper;

    private Room room;
    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
        room.setName("Living Room");
    }

    @Test
    @WithMockUser(username = "testuser")
    void testListRooms() throws Exception {
        Mockito.when(roomService.listRooms()).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/api/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(room.getId()))
                .andExpect(jsonPath("$[0].name").value(room.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetRoom() throws Exception {
        Mockito.when(roomService.getRoomById(1L)).thenReturn(room);

        mockMvc.perform(get("/api/room/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(room.getId()))
                .andExpect(jsonPath("$.name").value(room.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateRoom() throws Exception {
        Mockito.when(roomService.createRoom(Mockito.any(Room.class), Mockito.eq(1L))).thenReturn(room);

        mockMvc.perform(post("/api/room/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(room.getId()))
                .andExpect(jsonPath("$.name").value(room.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateRoom() throws Exception {
        Mockito.when(roomService.updateRoom(Mockito.eq(1L), Mockito.any(Room.class))).thenReturn(room);

        mockMvc.perform(put("/api/room/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(room.getId()))
                .andExpect(jsonPath("$.name").value(room.getName()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteRoom() throws Exception {
        Mockito.doNothing().when(roomService).deleteRoom(1L);

        mockMvc.perform(delete("/api/room/1"))
                .andExpect(status().isNoContent());
    }
}
