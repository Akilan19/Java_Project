package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "portfolios")
public class Portfolio {

    @Id
    private String id;

    private String userName;                  // UserName
    private List<PortfolioStockEntry> stocks; // array of stock entries
    private double totalPortfolioWorth;       // Total Portfolio worth

    public Portfolio() {
    }

    public Portfolio(String id, String userName, List<PortfolioStockEntry> stocks, double totalPortfolioWorth) {
        this.id = id;
        this.userName = userName;
        this.stocks = stocks;
        this.totalPortfolioWorth = totalPortfolioWorth;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PortfolioStockEntry> getStocks() {
        return stocks;
    }
    public void setStocks(List<PortfolioStockEntry> stocks) {
        this.stocks = stocks;
    }

    public double getTotalPortfolioWorth() {
        return totalPortfolioWorth;
    }
    public void setTotalPortfolioWorth(double totalPortfolioWorth) {
        this.totalPortfolioWorth = totalPortfolioWorth;
    }
}
