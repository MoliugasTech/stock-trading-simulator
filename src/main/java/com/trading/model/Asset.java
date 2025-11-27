package com.trading.model;

import java.util.ArrayList;
import java.util.List;

public class Asset {
    private String name;
    private double currentPrice;
    private List<Double> priceHistory;

    public Asset(String name, double initialPrice) {
        this.name = name;
        this.currentPrice = initialPrice;
        this.priceHistory = new ArrayList<>();
        this.priceHistory.add(initialPrice);
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void updatePrice(double newPrice) {
        this.currentPrice = newPrice;
        this.priceHistory.add(newPrice);
    }

    public List<Double> getPriceHistory() {
        return new ArrayList<>(priceHistory);
    }
}
