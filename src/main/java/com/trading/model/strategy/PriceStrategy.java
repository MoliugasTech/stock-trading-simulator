package com.trading.model.strategy;

public interface PriceStrategy {
    double generateNextPrice(double currentPrice);
}