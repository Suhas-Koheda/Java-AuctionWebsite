package com.AuctionWeb.AuctionWeb.repository;

import com.AuctionWeb.AuctionWeb.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
}
