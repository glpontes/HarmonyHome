package com.gr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gr.entity.Room;
import com.gr.exception.RoomNotFoundException;
import com.gr.repository.RoomRepository;

@Service
public class RoomService {

	private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long roomId){
        return roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
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
