package com.trading.game;

import com.trading.model.*;
import com.trading.model.strategy.TrendingStrategy;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntegrationTest {

    @Test
    public void testFullGameSimulation() {
        // Create game with trending market
        Player player = new Player(1000.0);
        Asset asset = AssetFactory.createStock("TEST", 100.0);
        Market market = new Market(new TrendingStrategy(0.01, 0.02));
        Game game = new Game(player, asset, market, 10);

        // Play full game
        while (!game.isGameOver()) {
            game.playTurn();
        }

        // Game should complete
        assertTrue(game.isGameOver());
        assertEquals(10, game.getCurrentTurn());

        // Portfolio value should be calculated
        double finalValue = player.getPortfolioValue(asset.getCurrentPrice());
        assertTrue(finalValue >= 0);
    }

    @Test
    public void testAssetWithObservers() {
        Asset asset = AssetFactory.createBitcoin();
        Market market = new Market(0.05);

        // Add observer
        var stats = new com.trading.model.observer.PriceStatistics();
        asset.addObserver(stats);

        // Generate price changes
        for (int i = 0; i < 5; i++) {
            double newPrice = market.generateNextPrice(asset.getCurrentPrice());
            asset.updatePrice(newPrice);
        }

        // Observer should have tracked all updates
        assertEquals(5, stats.getUpdateCount());
        assertTrue(stats.getMaxPrice() > 0);
        assertTrue(stats.getMinPrice() > 0);
    }

    @Test
    public void testMultipleAssetTypes() {
        Asset stock = AssetFactory.createAppleStock();
        Asset crypto = AssetFactory.createBitcoin();
        Asset commodity = AssetFactory.createGold();

        assertEquals(AssetType.STOCK, stock.getType());
        assertEquals(AssetType.CRYPTO, crypto.getType());
        assertEquals(AssetType.COMMODITY, commodity.getType());

        // All should have valid prices
        assertTrue(stock.getCurrentPrice() > 0);
        assertTrue(crypto.getCurrentPrice() > 0);
        assertTrue(commodity.getCurrentPrice() > 0);
    }
}