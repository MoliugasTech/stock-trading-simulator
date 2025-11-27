package com.trading.game;

public class GameConfig {
    // Game settings
    public static final int MAX_TURNS = 30;
    public static final double STARTING_CASH = 1000.0;
    public static final double STARTING_PRICE = 100.0;

    // Market settings
    public static final double VOLATILITY = 0.03; // 3%

    // Price history
    public static final int MAX_HISTORY_SIZE = 100;
    public static final int SMA_PERIOD = 10;

    // Chart settings
    public static final int CHART_WIDTH = 50;
    public static final int CHART_HEIGHT = 10;

    // Transaction fee (optional)
    public static final double TRANSACTION_FEE = 0.001; // 0.1%

    private GameConfig() {
        // Utility class - no instantiation
    }
}