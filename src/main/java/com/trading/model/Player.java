package com.trading.model;

public class Player {
    private double cash;
    private int sharesOwned;

    public Player(double initialCash) {
        this.cash = initialCash;
        this.sharesOwned = 0;
    }

    public double getCash() {
        return cash;
    }

    public int getSharesOwned() {
        return sharesOwned;
    }

    public boolean buyShares(int amount, double pricePerShare) {
        double cost = calculateCost(amount, pricePerShare);

        if (canAfford(cost)) {
            cash -= cost;
            sharesOwned += amount;
            return true;
        }

        return false;
    }

    public boolean sellShares(int amount, double pricePerShare) {
        if (hasEnoughShares(amount)) {
            double revenue = calculateCost(amount, pricePerShare);
            cash += amount * pricePerShare;
            sharesOwned -= amount;
            return true;
        }
        return false;
    }

    // Helper methods (DRY)
    private double calculateCost(int amount, double pricePerShare) {
        return amount * pricePerShare;
    }

    private boolean canAfford(double cost) {
        return cash >= cost;
    }

    private boolean hasEnoughShares(int amount) {
        return sharesOwned >= amount;
    }

    public double getPortfolioValue(double currentPrice) {
        return cash + (sharesOwned * currentPrice);
    }
}