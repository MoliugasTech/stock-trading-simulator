package com.trading.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class AssetTest {

    @Test
    public void testAssetCreation() {
        Asset asset = new Asset("AAPL", 150.0);

        assertEquals("AAPL", asset.getName());
        assertEquals(150.0, asset.getCurrentPrice(), 0.01);
    }

    @Test
    public void testUpdatePrice() {
        Asset asset = new Asset("BTC", 50000.0);

        asset.updatePrice(51000.0);

        assertEquals(51000.0, asset.getCurrentPrice(), 0.01);
    }

    @Test
    public void testPriceHistory() {
        Asset asset = new Asset("TSLA", 200.0);

        asset.updatePrice(210.0);
        asset.updatePrice(205.0);

        assertEquals(3, asset.getPriceHistory().size());
        assertEquals(200.0, asset.getPriceHistory().get(0), 0.01);
    }
}