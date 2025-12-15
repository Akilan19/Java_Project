package com.example.demo.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.demo.model.Portfolio;
import com.example.demo.repository.PortfolioRepo;

@Service
public class PortfolioService {

    private final PortfolioRepo portfolioRepo;

    public PortfolioService(PortfolioRepo portfolioRepo) {
        this.portfolioRepo = portfolioRepo;
    }

    public Portfolio getPortfolioForUser(String userName) {
        return portfolioRepo.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException("No portfolio found for user: " + userName));
    }
    
    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepo.save(portfolio);
    }
}
