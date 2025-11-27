package com.trading.game;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testGameCreation() {
        Game game = new Game(1000.0, 30);

        assertNotNull(game.getPlayer());
        assertNotNull(game.getAsset());
        assertEquals(0, game.getCurrentTurn());
        assertFalse(game.isGameOver());
    }

    @Test
    public void testGameProgression() {
        Game game = new Game(1000.0, 5);

        while (!game.isGameOver()) {
            game.playTurn();
        }

        assertTrue(game.getCurrentTurn() <= 5);
        assertTrue(game.isGameOver());
    }

    @Test
    public void testBankruptcy() {
        Game game = new Game(0.0, 30);

        game.playTurn();

        assertTrue(game.isGameOver());
    }
}