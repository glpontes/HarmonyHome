package com.gr.controller;

import com.gr.dto.UserDTO;
import com.gr.entity.User;
import com.gr.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    List<User> listUsers(){
        return userService.listUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{userId}")
    public UserDTO getUser(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return convertToDTO(user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/user")
    UserDTO createUser(@Valid @RequestBody UserDTO userDTO){
        User u = convertToEntity(userDTO);
        User saved = userService.createUser(u);
        return convertToDTO(saved);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/user/{userId}")
    public UserDTO updateTask(@PathVariable Long userId, @RequestBody UserDTO userDTO){

        User u = convertToEntity(userDTO);
        User userUpdated = userService.updateUser(userId, u);
        return convertToDTO(userUpdated);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }





    private UserDTO convertToDTO(User u) {
        return modelMapper.map(u, UserDTO.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
