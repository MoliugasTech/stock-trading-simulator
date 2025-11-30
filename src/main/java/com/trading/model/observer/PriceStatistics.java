package com.trading.model.observer;

import com.trading.model.Asset;

public class PriceStatistics implements PriceObserver {
    private int updateCount;
    private double totalChange;
    private double maxPrice;
    private double minPrice;

    public PriceStatistics() {
        this.updateCount = 0;
        this.totalChange = 0.0;
        this.maxPrice = Double.MIN_VALUE;
        this.minPrice = Double.MAX_VALUE;
    }

    @Override
    public void onPriceChange(Asset asset, double oldPrice, double newPrice) {
        updateCount++;
        totalChange += (newPrice - oldPrice);
        maxPrice = Math.max(maxPrice, newPrice);
        minPrice = Math.min(minPrice, newPrice);
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public double getAverageChange() {
        return updateCount > 0 ? totalChange / updateCount : 0.0;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void printStatistics() {
        System.out.printf("📊 Statistics: Updates=%d, Avg Change=$%.2f, Max=$%.2f, Min=$%.2f%n",
                updateCount, getAverageChange(), maxPrice, minPrice);
    }
}