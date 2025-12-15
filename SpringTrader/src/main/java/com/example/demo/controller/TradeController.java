package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BuyOrderRequest;
import com.example.demo.model.Portfolio;
import com.example.demo.service.TradeService;

@RestController
@RequestMapping("/orders")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody BuyOrderRequest request) {
        try {
            Portfolio updatedPortfolio = tradeService.processBuyOrder(request);
            return ResponseEntity.ok(updatedPortfolio);
        } catch (Exception e) {
            // For now, simple error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
