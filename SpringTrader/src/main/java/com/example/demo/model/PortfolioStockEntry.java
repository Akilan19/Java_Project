package com.example.demo.model;

public class PortfolioStockEntry {

    private String stockName;        // e.g., "SpringSoft"
    private int quantity;            // number of shares owned
    private double totalStockValue;  // quantity * price

    public PortfolioStockEntry() {
    }

    public PortfolioStockEntry(String stockName, int quantity, double totalStockValue) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.totalStockValue = totalStockValue;
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

    public double getTotalStockValue() {
        return totalStockValue;
    }
    public void setTotalStockValue(double totalStockValue) {
        this.totalStockValue = totalStockValue;
    }
}
