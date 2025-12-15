package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
public class Player {

    @Id
    private String id;

    private String username;
    private double availableBalance;

    public Player() {
    }

    public Player(String id, String username, double availableBalance) {
        this.id = id;
        this.username = username;
        this.availableBalance = availableBalance;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
