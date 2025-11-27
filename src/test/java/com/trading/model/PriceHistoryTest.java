package com.trading.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PriceHistoryTest {

    @Test
    public void testAddPrice() {
        PriceHistory history = new PriceHistory(50);

        history.addPrice(100.0);
        history.addPrice(105.0);
        history.addPrice(110.0);

        assertEquals(3, history.size());
        assertEquals(110.0, history.getLatest(), 0.01);
    }

    @Test
    public void testMaxSizeLimit() {
        PriceHistory history = new PriceHistory(5);

        // Add 10 prices
        for (int i = 1; i <= 10; i++) {
            history.addPrice(i * 10.0);
        }

        // Should keep only last 5
        assertEquals(5, history.size());
        assertEquals(100.0, history.getLatest(), 0.01);
        assertEquals(60.0, history.getAll().get(0), 0.01);
    }

    @Test
    public void testSMACalculation() {
        PriceHistory history = new PriceHistory(50);

        history.addPrice(100.0);
        history.addPrice(110.0);
        history.addPrice(120.0);

        // SMA(3) = (100 + 110 + 120) / 3 = 110
        assertEquals(110.0, history.getSMA(3), 0.01);
    }

    @Test
    public void testSMAWithInsufficientData() {
        PriceHistory history = new PriceHistory(50);

        history.addPrice(100.0);
        history.addPrice(110.0);

        // Asked for SMA(5), but only 2 data points
        // Should return SMA(2)
        assertEquals(105.0, history.getSMA(5), 0.01);
    }

    @Test
    public void testGetLast() {
        PriceHistory history = new PriceHistory(50);

        for (int i = 1; i <= 10; i++) {
            history.addPrice(i * 10.0);
        }

        // Get last 3 prices
        assertEquals(3, history.getLast(3).size());
        assertEquals(80.0, history.getLast(3).get(0), 0.01);
        assertEquals(90.0, history.getLast(3).get(1), 0.01);
        assertEquals(100.0, history.getLast(3).get(2), 0.01);
    }
}