package com.trading.game;

public class Main {
    public static void main(String[] args) {
        // Create and start game
        Game game = new Game(
                GameConfig.STARTING_CASH,
                GameConfig.MAX_TURNS
        );

        game.start();
    }
}
