package com.AuctionWeb.AuctionWeb.controller;

import com.AuctionWeb.AuctionWeb.model.Player;
import com.AuctionWeb.AuctionWeb.model.Room;
import com.AuctionWeb.AuctionWeb.service.RoomService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingtemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingtemplate = messagingTemplate;
    }
    @Autowired
    private RoomService roomService;

    @MessageMapping("/joinRoom")
    @SendTo("/topic/roomUpdates")
    public Room joinRoom(@Payload String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);
            String roomId = jsonNode.get("roomId").asText();
            Player player = objectMapper.treeToValue(jsonNode.get("player"), Player.class);

            // Add player to the room using service
            Room room = roomService.addPlayerToRoom(roomId, player);

            // Broadcast updated room data to all clients subscribed to the topic
            messagingtemplate.convertAndSend("/topic/roomUpdates", room);
            return room;
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return null; // Handle error appropriately
        }
    }


}