package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Portfolio;

public interface PortfolioRepo extends MongoRepository<Portfolio, String> {

    Optional<Portfolio> findByUserName(String userName);
}
