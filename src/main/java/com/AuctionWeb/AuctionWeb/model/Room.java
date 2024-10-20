package com.AuctionWeb.AuctionWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")
public class Room {
    @Id
    private String roomId;
    private List<Player> gamePlayers;
    private String currentAuctionPlayer;

    public Room() {
        this.gamePlayers = new ArrayList<>();
    }

    // Getter for roomId
    public String getRoomId() {
        return roomId;
    }

    // Setter for roomId
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    // Getter for gamePlayers
    public List<Player> getGamePlayers() {
        return gamePlayers;
    }

    // Setter for gamePlayers
    public void setGamePlayers(List<Player> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    // Getter for currentAuctionPlayer
    public String getCurrentAuctionPlayer() {
        return currentAuctionPlayer;
    }

    // Setter for currentAuctionPlayer
    public void setCurrentAuctionPlayer(String currentAuctionPlayer) {
        this.currentAuctionPlayer = currentAuctionPlayer;
    }
}
