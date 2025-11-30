package com.trading.model.strategy;

import java.util.Random;

public class RandomWalkStrategy implements PriceStrategy {
    private final Random random;
    private final double volatility;

    public RandomWalkStrategy(double volatility) {
        this.random = new Random();
        this.volatility = volatility;
    }

    @Override
    public double generateNextPrice(double currentPrice) {
        double changePercent = (random.nextDouble() - 0.5) * 2 * volatility;
        double newPrice = currentPrice * (1 + changePercent);
        return Math.max(0.01, newPrice);
    }
}