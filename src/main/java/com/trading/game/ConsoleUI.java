package com.trading.game;

import com.trading.model.Asset;
import com.trading.model.Player;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     STOCK TRADING SIMULATOR           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
    }

    public void displayGameState(int turn, int maxTurns, Player player, Asset asset) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Turn: " + turn + "/" + maxTurns);
        System.out.println("=".repeat(50));
        System.out.println();

        System.out.printf("Current Price: $%.2f%n", asset.getCurrentPrice());
        System.out.printf("SMA(10): $%.2f%n", asset.getSMA(10));
        System.out.println();

        System.out.println("💰 Your Portfolio:");
        System.out.printf("   Cash: $%.2f%n", player.getCash());
        System.out.printf("   Shares: %d%n", player.getSharesOwned());

        double portfolioValue = player.getCash() +
                (player.getSharesOwned() * asset.getCurrentPrice());
        System.out.printf("   Total Value: $%.2f%n", portfolioValue);
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
        System.out.println("\n" + "=".repeat(50));
        System.out.println("GAME OVER!");
        System.out.println("=".repeat(50));
        System.out.println();

        double finalValue = player.getCash() +
                (player.getSharesOwned() * asset.getCurrentPrice());
        double profit = finalValue - GameConfig.STARTING_CASH;
        double profitPercent = (profit / GameConfig.STARTING_CASH) * 100;

        System.out.printf("Final Portfolio Value: $%.2f%n", finalValue);
        System.out.printf("Profit/Loss: $%.2f (%.1f%%)%n", profit, profitPercent);
        System.out.printf("Turns played: %d%n", turns);
        System.out.println();

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