package com.AuctionWeb.AuctionWeb.repository;

import com.AuctionWeb.AuctionWeb.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Long> {}
