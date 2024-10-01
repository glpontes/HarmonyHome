package com.gr.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.gr.entity.User;
import com.gr.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.gr.entity.House;
import com.gr.exception.HouseNotFoundException;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.HouseRepository;
import com.gr.repository.RoomRepository;


@Service
public class HouseService {
	
	private HouseRepository houseRepository;
    private UserRepository userRepository;

	public HouseService(HouseRepository houseRepository, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.houseRepository = houseRepository;
	}
	
	public List<House> listHouses() {
        return houseRepository.findAll();
    }

    public House getHouseById(Long houseId){
        return houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException("House not found"));
    }

    public House createHouse(House house, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            house.setUser(userOptional.get());
            return houseRepository.save(house);
        }
        throw new UserNotFoundException("User not found");
    }

    public House updateHouse(Long houseId, House house) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isPresent()) {
            House houseToUpdate = houseOptional.get();
            houseToUpdate.setName(house.getName());
            houseToUpdate.setAddress(house.getAddress());
            return houseRepository.save(houseToUpdate);
        }
        throw new HouseNotFoundException("house not found");
    }

    public void deleteHouse(Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isPresent()) {
            House house = houseOptional.get();
            Collection<User> users = house.getUsers();
            users.stream().forEach(user -> {
                user.getHousesShared().remove(house);
                userRepository.save(user);
            });
            houseRepository.delete(house);
            return;
        }throw new HouseNotFoundException("House not found");
    }
	

}
