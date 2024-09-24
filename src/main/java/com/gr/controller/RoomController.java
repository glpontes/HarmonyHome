package com.gr.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gr.dto.RoomDTO;
import com.gr.entity.Room;
import com.gr.services.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

	private final RoomService roomService;
	private final ModelMapper modelMapper;


    public RoomController(RoomService roomService, ModelMapper modelMapper) {
		this.roomService = roomService;
		this.modelMapper = modelMapper;
	}

	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/room")
    List<Room> listRooms(){
        return roomService.listRooms();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/room/{roomId}")
    public RoomDTO getRoomr(@PathVariable Long roomId){
        Room room = roomService.getRoomById(roomId);
        return convertToDTO(room);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/room")
    RoomDTO createRoom(@Valid @RequestBody RoomDTO roomDTO){
        Room r = convertToEntity(roomDTO);
        Room saved = roomService.createRoom(r);
        return convertToDTO(saved);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/room/{roomId}")
    public RoomDTO updateRoom(@PathVariable Long roomId, @RequestBody RoomDTO roomDTO){

        Room r = convertToEntity(roomDTO);
        Room roomUpdated = roomService.updateRoom(roomId, r);
        return convertToDTO(roomUpdated);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/room/{roomId}")
    public void deleteRoom(@PathVariable Long roomId){
    	roomService.deleteRoom(roomId);
    }





    private RoomDTO convertToDTO(Room r) {
        return modelMapper.map(r, RoomDTO.class);
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        return modelMapper.map(roomDTO, Room.class);
    }
}
