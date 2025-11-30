package com.trading.model;

public class AssetFactory {

    // Factory method for stocks
    public static Asset createStock(String symbol, double initialPrice) {
        return new Asset(symbol, AssetType.STOCK, initialPrice);
    }

    // Factory method for cryptocurrencies
    public static Asset createCrypto(String symbol, double initialPrice) {
        return new Asset(symbol, AssetType.CRYPTO, initialPrice);
    }

    // Factory method for commodities
    public static Asset createCommodity(String name, double initialPrice) {
        return new Asset(name, AssetType.COMMODITY, initialPrice);
    }

    // Generic factory method
    public static Asset createAsset(String name, AssetType type, double initialPrice) {
        return new Asset(name, type, initialPrice);
    }

    // Predefined assets
    public static Asset createAppleStock() {
        return createStock("AAPL", 150.0);
    }

    public static Asset createBitcoin() {
        return createCrypto("BTC", 50000.0);
    }

    public static Asset createGold() {
        return createCommodity("GOLD", 1800.0);
    }

    private AssetFactory() {
        // Utility class - prevent instantiation
    }
}
