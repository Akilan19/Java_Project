package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BuyOrderRequest;
import com.example.demo.model.Player;
import com.example.demo.model.Portfolio;
import com.example.demo.model.PortfolioStockEntry;
import com.example.demo.model.Stock;
import com.example.demo.repository.PlayerRepo;
import com.example.demo.repository.PortfolioRepo;
import com.example.demo.repository.StockRepo;

@Service
public class TradeService {

    private final PlayerRepo playerRepo;
    private final StockRepo stockRepo;
    private final PortfolioRepo portfolioRepo;
    private final MongoTemplate mongoTemplate;

    public TradeService(PlayerRepo playerRepo,
                        StockRepo stockRepo,
                        PortfolioRepo portfolioRepo,
                        MongoTemplate mongoTemplate) {
        this.playerRepo = playerRepo;
        this.stockRepo = stockRepo;
        this.portfolioRepo = portfolioRepo;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Process a buy order with:
     * - price locking (uses price at the start of the transaction)
     * - atomic stock quantity decrement
     * - player balance update
     * - portfolio update
     */
    @Transactional
    public Portfolio processBuyOrder(BuyOrderRequest request) {

        // 1) Find player
        Player player = playerRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("Player not found: " + request.getUsername()));

        int qtyRequested = request.getQuantity();
        if (qtyRequested <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // 2) Find the stock (by companyName)
        Stock stock = stockRepo.findAll().stream()
                .filter(s -> s.getCompanyName().equalsIgnoreCase(request.getStockName()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Stock not found: " + request.getStockName()));

        double lockedPrice = stock.getStockPrice();      // 🔒 price locking
        double totalCost = lockedPrice * qtyRequested;

        if (player.getAvailableBalance() < totalCost) {
            throw new IllegalStateException("Insufficient balance for player: " + player.getUsername());
        }

        // 3) Atomically decrement stock.quantityAvailable IF enough is available
        Query q = new Query(Criteria.where("_id").is(stock.getId())
                .and("quantityAvailable").gte(qtyRequested));

        Update update = new Update().inc("quantityAvailable", -qtyRequested);

        var result = mongoTemplate.update(Stock.class)
                .matching(q)
                .apply(update)
                .first();

        if (result.getMatchedCount() == 0) {
            // Someone else got the last shares first
            throw new IllegalStateException("Not enough stock available for: " + stock.getCompanyName());
        }

        // 4) Deduct player's balance
        player.setAvailableBalance(player.getAvailableBalance() - totalCost);
        playerRepo.save(player);

        // 5) Update portfolio (or create if missing)
        Portfolio portfolio = portfolioRepo.findByUserName(player.getUsername())
                .orElseGet(() -> {
                    Portfolio p = new Portfolio();
                    p.setUserName(player.getUsername());
                    p.setStocks(new ArrayList<>());
                    p.setTotalPortfolioWorth(0.0);
                    return p;
                });

        List<PortfolioStockEntry> entries = portfolio.getStocks();
        PortfolioStockEntry entry = entries.stream()
                .filter(e -> e.getStockName().equalsIgnoreCase(stock.getCompanyName()))
                .findFirst()
                .orElse(null);

        if (entry == null) {
            entry = new PortfolioStockEntry(
                    stock.getCompanyName(),
                    qtyRequested,
                    totalCost   // total value of this stock position
            );
            entries.add(entry);
        } else {
            entry.setQuantity(entry.getQuantity() + qtyRequested);
            entry.setTotalStockValue(entry.getTotalStockValue() + totalCost);
        }

        portfolio.setTotalPortfolioWorth(portfolio.getTotalPortfolioWorth() + totalCost);
        portfolioRepo.save(portfolio);

        // 6) Return the updated portfolio as response
        return portfolio;
    }
}
