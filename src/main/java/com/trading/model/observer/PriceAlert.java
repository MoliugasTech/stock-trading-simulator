package com.trading.model.observer;

import com.trading.model.Asset;

public class PriceAlert implements PriceObserver {
    private final double threshold;
    private final boolean alertAbove;

    public PriceAlert(double threshold, boolean alertAbove) {
        this.threshold = threshold;
        this.alertAbove = alertAbove;
    }

    @Override
    public void onPriceChange(Asset asset, double oldPrice, double newPrice) {
        if (alertAbove && newPrice > threshold && oldPrice <= threshold) {
            System.out.printf("🚨 ALERT: %s price above $%.2f!%n",
                    asset.getName(), threshold);
        } else if (!alertAbove && newPrice < threshold && oldPrice >= threshold) {
            System.out.printf("🚨 ALERT: %s price below $%.2f!%n",
                    asset.getName(), threshold);
        }
    }
}