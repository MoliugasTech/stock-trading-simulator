package com.trading.model;

import java.util.Random;

public class Market {
    private Random random;
    private double volatility;

    public Market(double volatility) {
        this.random = new Random();
        this.volatility = volatility;
    }

    public double generateNextPrice(double currentPrice) {
        // Random walk: price changes by ±volatility%
        double changePercent = (random.nextDouble() - 0.5) * 2 * volatility;
        double newPrice = currentPrice * (1 + changePercent);

        // Prevent negative prices
        return Math.max(0.01, newPrice);
    }
}