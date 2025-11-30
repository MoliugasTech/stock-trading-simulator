package com.trading.model;

import com.trading.model.strategy.PriceStrategy;
import com.trading.model.strategy.RandomWalkStrategy;

public class Market {
    private PriceStrategy priceStrategy;

    public Market(double volatility) {
        this.priceStrategy = new RandomWalkStrategy(volatility);
    }

    public Market (PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    public double generateNextPrice(double currentPrice) {
       return priceStrategy.generateNextPrice(currentPrice);
    }

    public void setPriceStrategy(PriceStrategy strategy) {
        this.priceStrategy = strategy;
    }
}