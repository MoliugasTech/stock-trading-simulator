package com.trading.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testPlayerCanBeCreatedWithInitialCash() {
        Player player = new Player(1000.0);
        assertEquals(1000.0, player.getCash(), 0.01);
    }

    @Test
    public void testBuyShares() {
        Player player = new Player(1000.0);
        boolean success = player.buyShares(10, 50.0);

        assertTrue(success);
        assertEquals(500.0, player.getCash(), 0.01);
        assertEquals(10, player.getSharesOwned());
    }

    @Test
    public void testSellShares() {
        Player player = new Player(1000.0);
        player.buyShares(10, 50.0);

        boolean success = player.sellShares(5, 60.0);

        assertTrue(success);
        assertEquals(800.0, player.getCash(), 0.01);
        assertEquals(5, player.getSharesOwned());
    }

    @Test
    public void testBuySharesInsufficientFunds() {
        Player player = new Player(100.0);

        boolean success = player.buyShares(10, 50.0); // costs $500

        assertFalse(success);
        assertEquals(100.0, player.getCash(), 0.01); // unchanged
        assertEquals(0, player.getSharesOwned()); // unchanged
    }

    @Test
    public void testSellMoreSharesThanOwned() {
        Player player = new Player(1000.0);
        player.buyShares(5, 100.0);

        boolean success = player.sellShares(10, 100.0); // only has 5

        assertFalse(success);
        assertEquals(5, player.getSharesOwned()); // unchanged
    }

    @Test
    public void testGetPortfolioValue() {
        Player player = new Player(1000.0);
        player.buyShares(10, 50.0); // spend $500

        // Portfolio = $500 cash + 10 shares @ $60 = $1100
        double portfolioValue = player.getPortfolioValue(60.0);

        assertEquals(1100.0, portfolioValue, 0.01);
    }

    @Test
    public void testBuyZeroShares() {
        Player player = new Player(1000.0);

        boolean success = player.buyShares(0, 50.0);

        assertFalse(success);
        assertEquals(1000.0, player.getCash(), 0.01);
    }

    @Test
    public void testSellZeroShares() {
        Player player = new Player(1000.0);
        player.buyShares(10, 50.0);

        boolean success = player.sellShares(0, 50.0);

        assertFalse(success);
        assertEquals(10, player.getSharesOwned());
    }


}