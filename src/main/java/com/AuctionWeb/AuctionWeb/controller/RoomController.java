package com.AuctionWeb.AuctionWeb.controller;

import com.AuctionWeb.AuctionWeb.model.Player;
import com.AuctionWeb.AuctionWeb.model.Room;
import com.AuctionWeb.AuctionWeb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RoomController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RoomRepository roomRepository;

    @MessageMapping("/createRoom")
    public void createRoom(String data) {
        String[] parts = data.split(",");
        String roomId = parts[0];
        String playerName = parts[1];

        Room room = new Room();
        room.setRoomId(roomId);
        roomRepository.save(room);

        Player newPlayer = new Player();
        newPlayer.setName(playerName);


        if (room.getGamePlayers().size() < 25) { // Maximum 25 players
            room.getGamePlayers().add(newPlayer);
            roomRepository.save(room);

            String message = "Room " + roomId + " created successfully!";
            System.out.println(message);
            messagingTemplate.convertAndSend("/rooms/" + roomId, message);

             message = playerName + " joined room " + roomId;
            messagingTemplate.convertAndSend("/rooms/" + roomId, message);

        }
    }

    @MessageMapping("/joinRoom")
    public void joinRoom(String data) {
        String[] parts = data.split(",");
        String roomId = parts[0];
        String playerName = parts[1];

        Room room = roomRepository.findById(roomId).orElse(null);
        if (room != null) {
            Player newPlayer = new Player();
            newPlayer.setName(playerName);


            if (room.getGamePlayers().size() < 25) { // Maximum 25 players
                room.getGamePlayers().add(newPlayer);
                roomRepository.save(room);

                String message = playerName + " joined room " + roomId;
                messagingTemplate.convertAndSend("/rooms/" + roomId, message);
            } else {
                String message = "Room " + roomId + " is full!";
                messagingTemplate.convertAndSend("/rooms/" + roomId, message);
            }
        } else {
            String message = "Room " + roomId + " does not exist!";
            messagingTemplate.convertAndSend("/rooms/" + roomId, message);
        }
    }


    @MessageMapping("/disconnect")
    public void disconnect(String data) {
        String[] parts = data.split(",");
        String roomId = parts[0];
        String playerName = parts[1];

        Room room = roomRepository.findById(roomId).orElse(null);
        if (room != null) {
            // Remove the player from the room
            room.getGamePlayers().removeIf(player -> player.getName().equals(playerName));
            roomRepository.save(room);

            String message = playerName + " has disconnected from room " + roomId + ".";
            messagingTemplate.convertAndSend("/rooms/" + roomId, message);
        }
    }
}
