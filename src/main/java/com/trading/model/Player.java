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
        double cost = amount * pricePerShare;

        if (cash >= cost) {
            cash -= cost;
            sharesOwned += amount;
            return true;
        }

        return false;
    }

    public boolean sellShares(int amount, double pricePerShare) {
        if (sharesOwned >= amount) {
            cash += amount * pricePerShare;
            sharesOwned -= amount;
            return true;
        }

        return false;
    }
}