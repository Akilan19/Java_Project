package com.example.demo.dto;

public class BuyOrderRequest {

    private String username;
    private String stockName;
    private int quantity;

    public BuyOrderRequest() {
    }

    public BuyOrderRequest(String username, String stockName, int quantity) {
        this.username = username;
        this.stockName = stockName;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
