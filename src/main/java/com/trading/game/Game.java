package com.trading.game;

import com.trading.model.Asset;
import com.trading.model.Market;
import com.trading.model.Player;

public class Game {
    private Player player;
    private Asset asset;
    private Market market;
    private ConsoleUI ui;
    private int currentTurn;
    private int maxTurns;
    private boolean gameOver;

    public Game(double startingCash, int maxTurns) {
        this.player = new Player(startingCash);
        this.asset = new Asset("STOCK", GameConfig.STARTING_PRICE);
        this.market = new Market(GameConfig.VOLATILITY);
        this.ui = new ConsoleUI();
        this.currentTurn = 0;
        this.maxTurns = maxTurns;
        this.gameOver = false;
    }

    public Game(Player player, Asset asset, Market market, int maxTurns) {
        this.player = player;
        this.asset = asset;
        this.market = market;
        this.ui = null; // No UI for testing
        this.currentTurn = 0;
        this.maxTurns = maxTurns;
        this.gameOver = false;
    }

    public void start() {
        ui.displayWelcome();

        while (!gameOver && currentTurn < maxTurns) {
            playTurn();
        }

        ui.displayFinalStats(player, asset, currentTurn);
        ui.close();
    }

    public void playTurn() {
        // 1. Generate new price
        double newPrice = market.generateNextPrice(asset.getCurrentPrice());
        asset.updatePrice(newPrice);

        // 2. Display state
        if (ui != null) {
            ui.displayGameState(currentTurn + 1, maxTurns, player, asset);

            // 3. Get player action
            String action = ui.getPlayerAction();

            // 4. Process action
            processAction(action);
        }

        // 5. Check game over
        currentTurn++;
        checkGameOver();
    }

    private void processAction(String action) {
        switch (action) {
            case "B":
                int buyAmount = ui.getAmount("buy");
                boolean buySuccess = player.buyShares(buyAmount, asset.getCurrentPrice());
                if (buySuccess) {
                    ui.displayTransactionResult(true,
                            "Bought " + buyAmount + " shares at $" +
                                    String.format("%.2f", asset.getCurrentPrice()));
                } else {
                    ui.displayTransactionResult(false, "Insufficient funds!");
                }
                break;

            case "S":
                int sellAmount = ui.getAmount("sell");
                boolean sellSuccess = player.sellShares(sellAmount, asset.getCurrentPrice());
                if (sellSuccess) {
                    ui.displayTransactionResult(true,
                            "Sold " + sellAmount + " shares at $" +
                                    String.format("%.2f", asset.getCurrentPrice()));
                } else {
                    ui.displayTransactionResult(false, "Not enough shares!");
                }
                break;

            case "H":
                ui.displayTransactionResult(true, "Holding position...");
                break;

            default:
                ui.displayTransactionResult(false, "Invalid action!");
                break;
        }
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
}