package com.AuctionWeb.AuctionWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private String name;
    private int purse = 100; // Automatically set to 100
    private List<String> playersBought = new ArrayList<>(); // Initialize empty list

    public Player() {}

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurse() {
        return purse;
    }

    public List<String> getPlayersBought() {
        return playersBought;
    }

    public void setPlayersBought(List<String> playersBought) {
        if (playersBought.size() <= 25) {
            this.playersBought = playersBought;
        } else {
            throw new IllegalArgumentException("Maximum of 25 players can be bought.");
        }
    }
}
