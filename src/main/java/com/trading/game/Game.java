package com.trading.game;

import com.trading.model.Asset;
import com.trading.model.Market;
import com.trading.model.Player;

public class Game {
    private Player player;
    private Asset asset;
    private Market market;
    private int currentTurn;
    private int maxTurns;
    private boolean gameOver;

    public Game(double startingCash, int maxTurns) {
        this.player = new Player(startingCash);
        this.asset = new Asset("STOCK", 100.0);
        this.market = new Market(0.03); // 3% volatility
        this.currentTurn = 0;
        this.maxTurns = maxTurns;
        this.gameOver = false;
    }

    public void start() {
        // Main game loop will be here
        while (!gameOver && currentTurn < maxTurns) {
            playTurn();
        }
    }

    public void playTurn() {
        // 1. Generate new price
        double newPrice = market.generateNextPrice(asset.getCurrentPrice());
        asset.updatePrice(newPrice);

        // 2. Display state (will add UI later)

        // 3. Get player action (will add UI later)

        // 4. Process action

        // 5. Check game over
        currentTurn++;
        checkGameOver();
    }

    private void checkGameOver() {
        // Bankrupt: no cash and no shares
        if (player.getCash() <= 0 && player.getSharesOwned() == 0) {
            gameOver = true;
        }
    }

    public boolean isGameOver() {
        return gameOver || currentTurn >= maxTurns;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public Player getPlayer() {
        return player;
    }

    public Asset getAsset() {
        return asset;
    }

    public double getFinalProfit() {
        double totalValue = player.getCash() +
                (player.getSharesOwned() * asset.getCurrentPrice());
        return totalValue - 1000.0; // Starting cash was 1000
    }
}