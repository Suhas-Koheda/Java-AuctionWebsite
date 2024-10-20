package com.AuctionWeb.AuctionWeb.service;

import com.AuctionWeb.AuctionWeb.model.Player;
import com.AuctionWeb.AuctionWeb.model.Room;
import com.AuctionWeb.AuctionWeb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(String id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room updateRoom(String id, Room room) {
        if (roomRepository.existsById(id)) {
            room.setRoomId(id);
            return roomRepository.save(room);
        }
        return null;
    }

    public boolean deleteRoom(String id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Method to add a player to a specified room
    public Room addPlayerToRoom(String roomId, Player player) {
        Room room = getRoomById(roomId);
        if (room != null) {
            room.getGamePlayers().add(player); // Add the player to the room's player list
            return roomRepository.save(room); // Save the updated room
        }
        return null; // Return null if the room does not exist
    }
}
