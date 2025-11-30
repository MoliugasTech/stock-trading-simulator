package com.trading.model;

import java.util.List;

public class Asset {
    private final String name;
    private final AssetType type;
    private double currentPrice;
    private final PriceHistory priceHistory;

    public Asset(String name, AssetType type, double initialPrice) {
        this.name = name;
        this.type = type;
        this.currentPrice = initialPrice;
        this.priceHistory = new PriceHistory(100); // Keep last 100 prices
        this.priceHistory.addPrice(initialPrice);
    }

    // Old constructor for backward compatibility
    public Asset(String name, double initialPrice) {
        this(name, AssetType.STOCK, initialPrice);
    }

    public String getName() {
        return name;
    }

    public AssetType getType() {
        return type;
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