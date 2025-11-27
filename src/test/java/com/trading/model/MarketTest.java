package com.trading.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class MarketTest {

    @Test
    public void testPriceGeneration() {
        Market market = new Market(0.05); // 5% volatility
        double currentPrice = 100.0;

        double newPrice = market.generateNextPrice(currentPrice);

        // Price should be within reasonable range (±10%)
        assertTrue(newPrice > 90.0 && newPrice < 110.0);
        assertTrue(newPrice > 0); // Never negative
    }

    @Test
    public void testMultiplePriceUpdates() {
        Market market = new Market(0.03);
        Asset asset = new Asset("TEST", 100.0);

        // Generate 10 price updates
        for (int i = 0; i < 10; i++) {
            double newPrice = market.generateNextPrice(asset.getCurrentPrice());
            asset.updatePrice(newPrice);
        }

        // Should have 11 prices (initial + 10 updates)
        assertEquals(11, asset.getPriceHistory().size());
    }
}
