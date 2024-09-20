package com.gr.services;

import com.gr.entity.User;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        throw new UserNotFoundException("User not found");
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

}
