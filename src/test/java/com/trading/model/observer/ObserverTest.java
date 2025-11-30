package com.trading.model.observer;

import com.trading.model.Asset;
import com.trading.model.AssetFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class ObserverTest {

    @Test
    public void testObserverNotification() {
        Asset asset = AssetFactory.createStock("TEST", 100.0);
        PriceStatistics stats = new PriceStatistics();

        asset.addObserver(stats);

        asset.updatePrice(105.0);
        asset.updatePrice(110.0);
        asset.updatePrice(108.0);

        assertEquals(3, stats.getUpdateCount());
        assertEquals(110.0, stats.getMaxPrice(), 0.01);
        assertEquals(105.0, stats.getMinPrice(), 0.01);
    }

    @Test
    public void testMultipleObservers() {
        Asset asset = AssetFactory.createStock("TEST", 100.0);
        PriceStatistics stats = new PriceStatistics();
        PriceLogger logger = new PriceLogger();

        asset.addObserver(stats);
        asset.addObserver(logger);

        asset.updatePrice(105.0);

        assertEquals(1, stats.getUpdateCount());
    }

    @Test
    public void testRemoveObserver() {
        Asset asset = AssetFactory.createStock("TEST", 100.0);
        PriceStatistics stats = new PriceStatistics();

        asset.addObserver(stats);
        asset.updatePrice(105.0);

        asset.removeObserver(stats);
        asset.updatePrice(110.0);

        // Should only count first update
        assertEquals(1, stats.getUpdateCount());
    }
}