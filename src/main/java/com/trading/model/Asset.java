package com.trading.model;

import java.util.List;

public class Asset {
    private String name;
    private double currentPrice;
    private PriceHistory priceHistory;

    public Asset(String name, double initialPrice) {
        this.name = name;
        this.currentPrice = initialPrice;
        this.priceHistory = new PriceHistory(100); // Keep last 100 prices
        this.priceHistory.addPrice(initialPrice);
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void updatePrice(double newPrice) {
        this.currentPrice = newPrice;
        this.priceHistory.addPrice(newPrice);
    }

    public List<Double> getPriceHistory() {
        return priceHistory.getAll();
    }

    public double getSMA(int period) {
        return priceHistory.getSMA(period);
    }
}