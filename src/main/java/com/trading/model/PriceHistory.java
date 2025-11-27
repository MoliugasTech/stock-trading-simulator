package com.trading.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceHistory {
    private List<Double> prices;
    private int maxSize;

    public PriceHistory(int maxSize) {
        this.prices = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public void addPrice(double price) {
        prices.add(price);

        // Keep only last maxSize prices
        if (prices.size() > maxSize) {
            prices.remove(0);
        }
    }

    public List<Double> getAll() {
        return new ArrayList<>(prices);
    }

    public List<Double> getLast(int n) {
        if (n >= prices.size()) {
            return getAll();
        }

        int fromIndex = prices.size() - n;
        return new ArrayList<>(prices.subList(fromIndex, prices.size()));
    }

    public double getSMA(int period) {
        if (prices.isEmpty()) {
            return 0.0;
        }

        int actualPeriod = Math.min(period, prices.size());
        List<Double> recentPrices = getLast(actualPeriod);

        double sum = 0.0;
        for (double price : recentPrices) {
            sum += price;
        }

        return sum / actualPeriod;
    }

    public int size() {
        return prices.size();
    }

    public double getLatest() {
        if (prices.isEmpty()) {
            return 0.0;
        }
        return prices.get(prices.size() - 1);
    }
}