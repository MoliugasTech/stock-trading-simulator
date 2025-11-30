package com.trading.model.observer;

import com.trading.model.Asset;

public class PriceLogger implements PriceObserver {

    @Override
    public void onPriceChange(Asset asset, double oldPrice, double newPrice) {
        double changePercent = ((newPrice - oldPrice) / oldPrice) * 100;
        System.out.printf("[LOG] %s: $%.2f → $%.2f (%.2f%%)%n",
                asset.getName(), oldPrice, newPrice, changePercent);
    }
}