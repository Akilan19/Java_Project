package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class Stock {

    @Id
    private String id;                // MongoDB _id

    private String companyName;       // Company_name
    private double stockPrice;        // Stock price
    private int quantityAvailable;    // Quantity_available

    public Stock() {
    }

    public Stock(String id, String companyName, double stockPrice, int quantityAvailable) {
        this.id = id;
        this.companyName = companyName;
        this.stockPrice = stockPrice;
        this.quantityAvailable = quantityAvailable;
    }

    // Getters & setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getStockPrice() {
        return stockPrice;
    }
    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
