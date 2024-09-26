package com.gr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gr.entity.House;
import com.gr.entity.Room;
import com.gr.exception.RoomNotFoundException;
import com.gr.exception.UserNotFoundException;
import com.gr.repository.HouseRepository;
import com.gr.repository.RoomRepository;

@Service
public class RoomService {

	private RoomRepository roomRepository;
	private HouseRepository houseRepository;

    public RoomService(RoomRepository roomRepository, HouseRepository houseRepository) {
        this.roomRepository = roomRepository;
        this.houseRepository = houseRepository;
    }

    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long roomId){
        return roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public Room createRoom(Room room, Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isPresent()) {
        	room.setHouse(houseOptional.get());
        	return roomRepository.save(room);
        }
        throw new RoomNotFoundException("Room not found");
    }

    public Room updateRoom(Long roomId, Room room) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if(roomOptional.isPresent()) {
            Room roomToUpdate = roomOptional.get();
            roomToUpdate.setName(room.getName()); 
            return roomRepository.save(roomToUpdate);
        }
        throw new RoomNotFoundException("Room not found");
    }

    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));
        roomRepository.delete(room);
    }
}
