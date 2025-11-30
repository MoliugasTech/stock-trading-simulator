package com.trading.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class AssetFactoryTest {

    @Test
    public void testCreateStock() {
        Asset stock = AssetFactory.createStock("AAPL", 150.0);

        assertEquals("AAPL", stock.getName());
        assertEquals(AssetType.STOCK, stock.getType());
        assertEquals(150.0, stock.getCurrentPrice(), 0.01);
    }

    @Test
    public void testCreateCrypto() {
        Asset crypto = AssetFactory.createCrypto("BTC", 50000.0);

        assertEquals("BTC", crypto.getName());
        assertEquals(AssetType.CRYPTO, crypto.getType());
        assertEquals(50000.0, crypto.getCurrentPrice(), 0.01);
    }

    @Test
    public void testCreateCommodity() {
        Asset commodity = AssetFactory.createCommodity("GOLD", 1800.0);

        assertEquals("GOLD", commodity.getName());
        assertEquals(AssetType.COMMODITY, commodity.getType());
        assertEquals(1800.0, commodity.getCurrentPrice(), 0.01);
    }

    @Test
    public void testPredefinedAssets() {
        Asset apple = AssetFactory.createAppleStock();
        Asset bitcoin = AssetFactory.createBitcoin();
        Asset gold = AssetFactory.createGold();

        assertNotNull(apple);
        assertNotNull(bitcoin);
        assertNotNull(gold);

        assertEquals(AssetType.STOCK, apple.getType());
        assertEquals(AssetType.CRYPTO, bitcoin.getType());
        assertEquals(AssetType.COMMODITY, gold.getType());
    }
}
