package com.gr.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gr.dto.HouseDTO;
import com.gr.dto.UserDTO;
import com.gr.entity.House;
import com.gr.entity.User;
import com.gr.services.HouseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/houses")
public class HouseController {
	
	private final HouseService houseService;
	private final ModelMapper modelMapper;
	
	
	public HouseController(HouseService houseService, ModelMapper modelMapper) {
		super();
		this.houseService = houseService;
		this.modelMapper = modelMapper;
	}
	
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/house")
    List<House> listHouses(){
        return houseService.listHouses();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{houseId}")
    public HouseDTO getHouse(@PathVariable Long houseId){
        House house = houseService.getHouseById(houseId);
        return convertToDTO(house);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/house")
    HouseDTO createHouse(@Valid @RequestBody HouseDTO houseDTO){
        House h = convertToEntity(houseDTO);
        House saved = houseService.createHouse(h);
        return convertToDTO(saved);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/house/{houseId}")
    public HouseDTO updateHouse(@PathVariable Long houseId, @RequestBody HouseDTO houseDTO){

        House h = convertToEntity(houseDTO);
        House houseUpdated = houseService.updateHouse(houseId, h);
        return convertToDTO(houseUpdated);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/house/{houseId}")
    public void deleteHouse(@PathVariable Long houseId){
    	houseService.deleteHouse(houseId);
    }





    private HouseDTO convertToDTO(House h) {
        return modelMapper.map(h, HouseDTO.class);
    }

    private House convertToEntity(HouseDTO houseDTO) {
        return modelMapper.map(houseDTO, House.class);
    }
	
	
	
	
}
