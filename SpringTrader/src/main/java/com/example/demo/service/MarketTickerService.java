package com.example.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepo;

@Service
public class MarketTickerService {

    private final StockRepo stockRepo;
    private final Random random = new Random();

    public MarketTickerService(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    /**
     * Runs every 20 seconds and updates stock prices by ±5%.
     */
    @Scheduled(fixedRate = 20_000)
    public void updateStockPrices() {
        List<Stock> stocks = stockRepo.findAll();

        for (Stock stock : stocks) {
            double oldPrice = stock.getStockPrice();
            if (oldPrice <= 0) {
                continue;
            }

            double pctChange = (random.nextDouble() * 0.10) - 0.05;

            double newPrice = oldPrice * (1.0 + pctChange);

            newPrice = Math.round(newPrice * 100.0) / 100.0;

            stock.setStockPrice(newPrice);
        }

        if (!stocks.isEmpty()) {
            stockRepo.saveAll(stocks);
        }
    }
}
