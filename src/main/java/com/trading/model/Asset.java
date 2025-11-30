package com.trading.model;

import com.trading.model.observer.PriceObserver;
import java.util.ArrayList;
import java.util.List;

public class Asset {
    private final String name;
    private final AssetType type;
    private double currentPrice;
    private final PriceHistory priceHistory;
    private final List<PriceObserver> observers;

    public Asset(String name, AssetType type, double initialPrice) {
        this.name = name;
        this.type = type;
        this.currentPrice = initialPrice;
        this.priceHistory = new PriceHistory(100); // Keep last 100 prices
        this.priceHistory.addPrice(initialPrice);
        this.observers = new ArrayList<>();
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
        double oldPrice = this.currentPrice;
        this.currentPrice = newPrice;
        this.priceHistory.addPrice(newPrice);

        // Notify all observers
        notifyObservers(oldPrice, newPrice);
    }

    public List<Double> getPriceHistory() {
        return priceHistory.getAll();
    }

    public double getSMA(int period) {
        return priceHistory.getSMA(period);
    }

    // Observer pattern methods
    public void addObserver(PriceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PriceObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(double oldPrice, double newPrice) {
        for (PriceObserver observer : observers) {
            observer.onPriceChange(this, oldPrice, newPrice);
        }
    }
}