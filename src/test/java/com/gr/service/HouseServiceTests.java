package com.gr.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gr.entity.House;
import com.gr.entity.User;
import com.gr.exception.HouseNotFoundException;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.HouseRepository;
import com.gr.repository.UserRepository;
import com.gr.services.HouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension .class)
public class HouseServiceTests {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HouseService houseService;

    private House house;
    private User user;

    @BeforeEach
    public void setUp() {

        house = new House();
        house.setId(1L);
        house.setName("Test House");
        house.setAddress("123 Test St");

        user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setHousesShared(new ArrayList<>(Arrays.asList(house)));

        house.setUsers(new ArrayList<>(Arrays.asList(user)));
    }

    @Test
    public void testListHouses() {
        when(houseRepository.findAll()).thenReturn(Arrays.asList(house));

        List<House> houses = houseService.listHouses();

        assertEquals(1, houses.size());
    }

    @Test
    public void testGetHouseById_HouseExists() {
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));

        House result = houseService.getHouseById(1L);

        assertEquals(house, result);
    }

    @Test
    public void testGetHouseById_HouseNotFound() {
        when(houseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HouseNotFoundException.class, () -> houseService.getHouseById(1L));
    }

    @Test
    public void testCreateHouse_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(houseRepository.save(house)).thenReturn(house);

        House result = houseService.createHouse(house, 1L);

        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    @Test
    public void testCreateHouse_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> houseService.createHouse(house, 1L));
    }

    @Test
    public void testUpdateHouse_HouseExists() {
        House houseUpdate = new House();
        houseUpdate.setName("Updated House");
        houseUpdate.setAddress("456 Updated St");

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(houseRepository.save(house)).thenReturn(house);

        House updatedHouse = houseService.updateHouse(1L, houseUpdate);

        assertEquals("Updated House", updatedHouse.getName());
        assertEquals("456 Updated St", updatedHouse.getAddress());
    }

    @Test
    public void testUpdateHouse_HouseNotFound() {
        House houseUpdate = new House();

        when(houseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HouseNotFoundException.class, () -> houseService.updateHouse(1L, houseUpdate));
    }

    @Test
    public void testDeleteHouse_HouseExists() {

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));

        doNothing().when(houseRepository).delete(any(House.class));

        houseService.deleteHouse(1L);
        verify(houseRepository, times(1)).delete(house);
    }

    @Test
    public void testDeleteHouse_HouseNotFound() {
        when(houseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HouseNotFoundException.class, () -> houseService.deleteHouse(1L));
    }
}