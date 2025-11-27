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
}