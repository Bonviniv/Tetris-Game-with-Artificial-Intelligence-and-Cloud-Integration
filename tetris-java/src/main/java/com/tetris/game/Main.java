package com.tetris.game;

public class Main {
    public static void main(String[] args) {
        boolean showGame = true;
        GameEngine game = new GameEngine(showGame);
        
        // Keep the main thread alive while the game is running
        while (!game.isGameOver()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        game.stopGame();
    }
}