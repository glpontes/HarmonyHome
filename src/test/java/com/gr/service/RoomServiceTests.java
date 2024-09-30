package com.gr.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.gr.entity.House;
import com.gr.entity.Room;
import com.gr.exception.HouseNotFoundException;
import com.gr.exception.RoomNotFoundException;
import com.gr.repository.HouseRepository;
import com.gr.repository.RoomRepository;
import com.gr.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;
    private House house;

    @BeforeEach
    public void setUp() {
        house = new House();
        house.setId(1L);

        room = new Room();
        room.setId(1L);
        room.setName("Living Room");
    }

    @Test
    public void testListRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));

        List<Room> rooms = roomService.listRooms();

        assertEquals(1, rooms.size());
    }

    @Test
    public void testGetRoomById_RoomExists() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Room foundRoom = roomService.getRoomById(1L);

        assertEquals(room, foundRoom);
    }

    @Test
    public void testGetRoomById_RoomDoesNotExist() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.getRoomById(1L));
    }

    @Test
    public void testCreateRoom_HouseExists() {
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room createdRoom = roomService.createRoom(room, 1L);

        assertNotNull(createdRoom);
        assertEquals(house, room.getHouse());
    }

    @Test
    public void testCreateRoom_HouseDoesNotExist() {
        when(houseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HouseNotFoundException.class, () -> roomService.createRoom(room, 1L));
    }

    @Test
    public void testUpdateRoom_RoomExists() {
        Room roomUpdate = new Room();
        roomUpdate.setName("Updated Room");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);

        Room updatedRoom = roomService.updateRoom(1L, roomUpdate);

        assertEquals("Updated Room", updatedRoom.getName());
    }

    @Test
    public void testUpdateRoom_RoomDoesNotExist() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.updateRoom(1L, room));
    }

    @Test
    public void testDeleteRoom_RoomExists() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        doNothing().when(roomRepository).delete(any(Room.class));

        roomService.deleteRoom(1L);
        verify(roomRepository, times(1)).delete(room);
    }

    @Test
    public void testDeleteRoom_RoomDoesNotExist() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.deleteRoom(1L));
    }
}
