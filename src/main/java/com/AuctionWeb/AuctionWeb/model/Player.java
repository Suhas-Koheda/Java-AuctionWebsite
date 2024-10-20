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

    // Getters and Setters
}