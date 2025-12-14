package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepo;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockRepo stockRepo;

    @PostMapping
    public Stock create(@RequestBody Stock stock) {
        return stockRepo.save(stock);
    }

    @GetMapping
    public List<Stock> findAll() {
        return stockRepo.findAll();
    }
}
