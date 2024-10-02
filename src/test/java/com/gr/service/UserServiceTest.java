package com.gr.service;

import com.gr.entity.House;
import com.gr.entity.User;
import com.gr.exception.EntityNotFoundException;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.HouseRepository;
import com.gr.repository.UserRepository;
import com.gr.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private House house;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        house = new House();
        house.setId(2L);
        house.setName("Test House");
        house.setUser(user);

        user.setHouses(new ArrayList<>(Arrays.asList(house)));
        user.setHousesShared(new ArrayList<>(Arrays.asList(house)));

    }

    @Test
    public void testListUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.listUsers();

        assertEquals(1, users.size());
    }

    @Test
    public void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser_UserExists() {
        User userUpdate = new User();
        userUpdate.setName("Updated Name");
        userUpdate.setEmail("updated@example.com");
        userUpdate.setUsername("updatedusername");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(1L, userUpdate);

        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals("updatedusername", updatedUser.getUsername());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        User userUpdate = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, userUpdate));
    }

    @Test
    public void testDeleteUser_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        doNothing().when(userRepository).delete(any(User.class));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    public void testShare_UserAndHouseExist() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.share(1L, 1L);

        assertNotNull(result);
        assertTrue(result.getHousesShared().contains(house));
    }

    @Test
    public void testShare_UserOrHouseNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.share(1L, 1L));
    }

    @Test
    public void testUnshare_UserAndHouseExist() {
        house.setUsers(new ArrayList<>(Arrays.asList(user)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.unshare(1L, 1L);

        assertNotNull(result);
        assertFalse(result.getHousesShared().contains(house));
    }

    @Test
    public void testUnshare_UserOrHouseNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.unshare(1L, 1L);
        });


    }
}