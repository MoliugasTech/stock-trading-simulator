package com.trading.model.observer;

import com.trading.model.Asset;

public interface PriceObserver {
    void onPriceChange(Asset asset, double oldPrice, double newPrice);
}