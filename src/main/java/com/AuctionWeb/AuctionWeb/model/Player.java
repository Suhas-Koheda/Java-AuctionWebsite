package com.AuctionWeb.AuctionWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "players")
public class Player {
    @Id
    private String id;

    private String name;
    private int purse;
    private List<String> playersBought;

    // Constructor
    public Player() {
    }

    public Player(String id, String name, int purse, List<String> playersBought) {
        this.id = id;
        this.name = name;
        this.purse = purse;
        this.playersBought = playersBought;
    }

    // Getters and Setters
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

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public List<String> getPlayersBought() {
        return playersBought;
    }

    public void setPlayersBought(List<String> playersBought) {
        this.playersBought = playersBought;
    }
}
