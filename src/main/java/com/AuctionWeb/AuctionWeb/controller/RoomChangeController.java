package com.AuctionWeb.AuctionWeb.controller;

import com.AuctionWeb.AuctionWeb.model.Room;
import com.AuctionWeb.AuctionWeb.service.MongoListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/room-changes")
public class RoomChangeController {

    private final MongoListener mongoListener;

    @Autowired
    public RoomChangeController(MongoListener mongoListener) {
        this.mongoListener = mongoListener;
    }

    @GetMapping
    public List<Room> getRoomChanges() {
        List<Room> roomChanges=mongoListener.getRoomChanges();
            System.out.println("Number of room changes: " + roomChanges.size());
            return new ArrayList<>(roomChanges);
    }
}
