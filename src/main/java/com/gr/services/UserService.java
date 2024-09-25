package com.gr.services;

import com.gr.entity.House;
import com.gr.entity.User;
import com.gr.exception.EntityNotFoundException;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.HouseRepository;
import com.gr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private HouseRepository houseRepository;


    public UserService(UserRepository userRepository, HouseRepository houseRepository) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
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

    public User share(Long houseId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(userOptional.isPresent() && houseOptional.isPresent()) {
            User user = userOptional.get();
            user.getHousesShared().add(houseOptional.get());
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("House or User not found");
    }

    public User unshare(Long houseId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(userOptional.isPresent() && houseOptional.isPresent()) {
            User user = userOptional.get();
            user.getHousesShared().remove(houseOptional.get());
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("House or User not found");
    }

}
