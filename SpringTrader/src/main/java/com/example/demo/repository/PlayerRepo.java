package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Player;

public interface PlayerRepo extends MongoRepository<Player, String> {

	Optional<Player> findByUsername(String username);
}
