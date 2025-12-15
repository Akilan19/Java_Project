package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Portfolio;
import com.example.demo.model.PortfolioStockEntry;
import com.example.demo.service.PortfolioService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

	@Autowired
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // GET /portfolio/{username}
    @GetMapping("/{userName}")
    public ResponseEntity<?> getPortfolio(@PathVariable String userName) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioForUser(userName);
            
            String username = portfolio.getUserName();
            List<PortfolioStockEntry> stock = portfolio.getStocks();
            double total = portfolio.getTotalPortfolioWorth();
            
            System.out.println(username);
            for(int i=0 ; i<stock.size() ; i++)
            {
            	System.out.println(stock.get(i));
            }
            System.out.println(total);
            
            return ResponseEntity.ok(portfolio);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No portfolio found for user: " + userName);
        }
    }
    
    @PostMapping
    public Portfolio setPortfolio(@RequestBody Portfolio p)
    {
    	return portfolioService.savePortfolio(p);
    }
}
