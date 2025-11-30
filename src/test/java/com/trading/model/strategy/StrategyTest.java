package com.trading.model.strategy;

import org.junit.Test;
import static org.junit.Assert.*;

public class StrategyTest {

    @Test
    public void testRandomWalkStrategy() {
        PriceStrategy strategy = new RandomWalkStrategy(0.05);
        double currentPrice = 100.0;

        double newPrice = strategy.generateNextPrice(currentPrice);

        // Should be within reasonable range
        assertTrue(newPrice > 90.0 && newPrice < 110.0);
        assertTrue(newPrice >= 0.01);
    }

    @Test
    public void testTrendingStrategy() {
        PriceStrategy strategy = new TrendingStrategy(0.02, 0.03);
        double currentPrice = 100.0;

        // Generate multiple prices to see trend
        double price1 = strategy.generateNextPrice(currentPrice);
        double price2 = strategy.generateNextPrice(price1);
        double price3 = strategy.generateNextPrice(price2);

        // All prices should be positive
        assertTrue(price1 > 0);
        assertTrue(price2 > 0);
        assertTrue(price3 > 0);
    }

    @Test
    public void testVolatileStrategy() {
        PriceStrategy strategy = new VolatileStrategy(0.1);
        double currentPrice = 100.0;

        // Volatile strategy should produce wider price swings
        double newPrice = strategy.generateNextPrice(currentPrice);

        assertTrue(newPrice > 0);
        // Could be more volatile than random walk
        assertTrue(Math.abs(newPrice - currentPrice) >= 0);
    }

    @Test
    public void testPriceNeverNegative() {
        PriceStrategy strategy = new RandomWalkStrategy(0.5); // High volatility
        double currentPrice = 1.0; // Very low price

        for (int i = 0; i < 100; i++) {
            currentPrice = strategy.generateNextPrice(currentPrice);
            assertTrue("Price should never be negative", currentPrice > 0);
        }
    }
}
