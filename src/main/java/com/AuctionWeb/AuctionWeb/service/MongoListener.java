package com.AuctionWeb.AuctionWeb.service;

import com.AuctionWeb.AuctionWeb.model.Room;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.messaging.ChangeStreamRequest;
import org.springframework.data.mongodb.core.messaging.MessageListener;
import org.springframework.data.mongodb.core.messaging.MessageListenerContainer;
import org.springframework.data.mongodb.core.messaging.Subscription;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class MongoListener {

    private final MessageListenerContainer messageListenerContainer;

    @Autowired
    public MongoListener(MessageListenerContainer messageListenerContainer) {
        this.messageListenerContainer = messageListenerContainer;
    }

    @PostConstruct
    public void registerListener() {
        MessageListener<ChangeStreamDocument<Document>, Room> messageListener = (message) -> {
            System.out.println("Hello " + message.getBody().getRoomId());
        };

        ChangeStreamRequest<Room> request = ChangeStreamRequest.builder()
                .collection("rooms")
                .filter(newAggregation(match(where("operationType").is("insert"))))
                .publishTo(messageListener)
                .build();

        Subscription subscription = messageListenerContainer.register(request, Room.class);
    }
}
