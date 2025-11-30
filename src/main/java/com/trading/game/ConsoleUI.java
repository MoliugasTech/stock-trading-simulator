package com.trading.game;

import com.trading.model.Asset;
import com.trading.model.Player;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    private void printHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(title);
        System.out.println("=".repeat(50));
    }

    private void printPrice(String label, double price) {
        System.out.printf("%s: $%.2f%n", label, price);
    }

    private void printPortfolioLine(String label, String value) {
        System.out.printf("   %s: %s%n", label, value);
    }

    public void displayWelcome() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     STOCK TRADING SIMULATOR           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
    }

    public void displayGameState(int turn, int maxTurns, Player player, Asset asset) {
        printHeader("Turn: " + turn + "/" + maxTurns);

        printPrice("Current Price", asset.getCurrentPrice());
        printPrice("SMA(10)", asset.getSMA(10));
        System.out.println();

        System.out.println("💰 Your Portfolio:");
        printPortfolioLine("Cash", String.format("$%.2f", player.getCash()));
        printPortfolioLine("Shares", String.valueOf(player.getSharesOwned()));

        double portfolioValue = player.getPortfolioValue(asset.getCurrentPrice());
        printPortfolioLine("Total Value", String.format("$%.2f", portfolioValue));
        System.out.println();
    }

    public String getPlayerAction() {
        System.out.println("Actions:");
        System.out.println("  [B] Buy");
        System.out.println("  [S] Sell");
        System.out.println("  [H] Hold");
        System.out.print("\nYour choice: ");

        String input = scanner.nextLine().trim().toUpperCase();
        return input;
    }

    public int getAmount(String action) {
        System.out.print("How many shares? ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void displayTransactionResult(boolean success, String message) {
        if (success) {
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message);
        }
    }

    public void displayFinalStats(Player player, Asset asset, int turns) {
        printHeader("GAME OVER!");

        double finalValue = player.getPortfolioValue(asset.getCurrentPrice());
        double profit = finalValue - GameConfig.STARTING_CASH;
        double profitPercent = (profit / GameConfig.STARTING_CASH) * 100;

        printPrice("Final Portfolio Value", finalValue);
        System.out.printf("Profit/Loss: $%.2f (%.1f%%)%n", profit, profitPercent);
        System.out.printf("Turns played: %d%n", turns);
        System.out.println();

        printGameResult(profit);
    }

    private void printGameResult(double profit) {
        if (profit > 0) {
            System.out.println("🎉 Congratulations! You made a profit!");
        } else if (profit < 0) {
            System.out.println("📉 Better luck next time!");
        } else {
            System.out.println("📊 You broke even!");
        }
    }

    public void close() {
        scanner.close();
    }
}