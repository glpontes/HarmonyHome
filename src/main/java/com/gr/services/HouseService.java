package com.gr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gr.entity.House;
import com.gr.exception.HouseNotFoundException;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.HouseRepository;


@Service
public class HouseService {
	
	private HouseRepository houseRepository;

	public HouseService(HouseRepository houseRepository) {
		super();
		this.houseRepository = houseRepository;
	}
	
	public List<House> listHouses() {
        return houseRepository.findAll();
    }

    public House getHouseById(Long houseId){
        return houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException("House not found"));
    }

    public House createHouse(House house) {
        return houseRepository.save(house);
    }

    public House updateHouse(Long houseId, House house) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isPresent()) {
            House houseToUpdate = houseOptional.get();
            houseToUpdate.setAdress(house.getAdress());
            return houseRepository.save(houseToUpdate);
        }
        throw new HouseNotFoundException("house not found");
    }

    public void deleteHouse(Long userId) {
        House user = houseRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        houseRepository.delete(user);
    }
	

}
