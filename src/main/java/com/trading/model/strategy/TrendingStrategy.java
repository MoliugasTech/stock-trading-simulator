package com.trading.model.strategy;

import java.util.Random;

public class TrendingStrategy implements PriceStrategy {
    private final Random random;
    private final double trendStrength;
    private final double volatility;
    private double currentTrend;

    public TrendingStrategy(double trendStrength, double volatility) {
        this.random = new Random();
        this.trendStrength = trendStrength;
        this.volatility = volatility;
        this.currentTrend = (random.nextBoolean() ? 1 : -1) * trendStrength;
    }

    @Override
    public double generateNextPrice(double currentPrice) {
        // Occasionally reverse trend
        if (random.nextDouble() < 0.1) {
            currentTrend = -currentTrend;
        }

        double randomWalk = (random.nextDouble() - 0.5) * 2 * volatility;
        double change = currentTrend + randomWalk;
        double newPrice = currentPrice * (1 + change);

        return Math.max(0.01, newPrice);
    }
}