package com.trading.model.strategy;

import java.util.Random;

public class VolatileStrategy implements PriceStrategy {
    private final Random random;
    private final double baseVolatility;

    public VolatileStrategy(double baseVolatility) {
        this.random = new Random();
        this.baseVolatility = baseVolatility;
    }

    @Override
    public double generateNextPrice(double currentPrice) {
        // High volatility with occasional spikes
        double volatility = baseVolatility * (1 + random.nextDouble());
        double changePercent = (random.nextDouble() - 0.5) * 2 * volatility;
        double newPrice = currentPrice * (1 + changePercent);

        return Math.max(0.01, newPrice);
    }
}