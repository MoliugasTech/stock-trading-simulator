package com.trading.game;

import com.trading.model.Asset;
import com.trading.model.Market;
import com.trading.model.Player;
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
        Player player = new Player(1000.0);
        Asset asset = new Asset("TEST", 100.0);
        Market market = new Market(0.03);
        Game game = new Game(player, asset, market, 5);

        while (!game.isGameOver()) {
            game.playTurn();
        }

        assertTrue(game.getCurrentTurn() <= 5);
        assertTrue(game.isGameOver());
    }

    @Test
    public void testBankruptcy() {
        Player player = new Player(0.0); // No money
        Asset asset = new Asset("TEST", 100.0);
        Market market = new Market(0.03);
        Game game = new Game(player, asset, market, 30);

        game.playTurn();

        assertTrue(game.isGameOver());
    }
}