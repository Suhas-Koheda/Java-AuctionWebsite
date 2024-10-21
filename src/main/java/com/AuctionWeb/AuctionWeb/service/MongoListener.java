package com.AuctionWeb.AuctionWeb.service;

import com.AuctionWeb.AuctionWeb.model.Room;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.messaging.ChangeStreamRequest;
import org.springframework.data.mongodb.core.messaging.MessageListener;
import org.springframework.data.mongodb.core.messaging.MessageListenerContainer;
import org.springframework.data.mongodb.core.messaging.Subscription;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class MongoListener {

    private final MessageListenerContainer messageListenerContainer;
    private final List<Room> roomChanges = new ArrayList<>();

    @Autowired
    public MongoListener(MessageListenerContainer messageListenerContainer) {
        System.out.println("MongoListener constructor called");
        this.messageListenerContainer = messageListenerContainer;
        setupChangeStreamListener();
    }

    private void setupChangeStreamListener() {
        System.out.println("Setting up change stream listener...");
        MessageListener<ChangeStreamDocument<Document>, Room> messageListener = (message) -> {
            System.out.println("Received message: " + message);
            Room room = message.getBody();
            if (room != null) {
                System.out.println("Room detected: " + room.getRoomId());
                roomChanges.add(room);
            } else {
                System.out.println("Received null message.");
            }
        };

    ChangeStreamRequest<Room> request = ChangeStreamRequest.builder()
                .collection("rooms")
            .filter(newAggregation(match(where("operationType").in("insert", "update", "replace"))))
                .publishTo(messageListener)
                .build();

        Subscription subscription = messageListenerContainer.register(request, Room.class);
        System.out.println("Change stream registered successfully");
    }

    public List<Room> getRoomChanges() {
        return new ArrayList<>(roomChanges); // Return a copy to avoid direct modification
    }
}
