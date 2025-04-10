package com.tetris.game;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;


public class GameEngine {
    private Board board;
    private Queue queue;
    private Piece currentPiece;
    private boolean showGame;
    private Timer fallTimer;
    private boolean isGameOver;
    private static final int FALL_DELAY = 1000; // 1 second in milliseconds
    private GUI gui;

    public GameEngine(boolean showGame) {
        this.showGame = showGame;
        this.board = new Board();
        this.queue = new Queue(board);
        this.isGameOver = false;
        if (showGame) {
            this.gui = new GUI(this);
        }
        initializeGame();
    }

    private void initializeGame() {
        spawnNewPiece();
        if (!isGameOver) {
            this.fallTimer = new Timer();
            startFallTimer();
            if (showGame) {
                SwingUtilities.invokeLater(() -> gui.refresh());
            }
        }
    }

    private void startFallTimer() {
        fallTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isGameOver && currentPiece != null) {
                    SwingUtilities.invokeLater(() -> {
                        movePieceDown();
                        if (showGame) {
                            gui.refresh();
                        }
                    });
                }
            }
        }, 0, FALL_DELAY);  // Start immediately (0 delay) and repeat every FALL_DELAY
    }

    public void handleInput(String action) {
        if (isGameOver || currentPiece == null) return;

        switch (action.toLowerCase()) {
            case "left":
                if (board.canPlacePiece(currentPiece, currentPiece.getX() - 1, currentPiece.getY())) {
                    board.removePiece(currentPiece);
                    currentPiece.setX(currentPiece.getX() - 1);
                    board.placePiece(currentPiece);
                }
                break;
            case "right":
                if (board.canPlacePiece(currentPiece, currentPiece.getX() + 1, currentPiece.getY())) {
                    board.removePiece(currentPiece);
                    currentPiece.setX(currentPiece.getX() + 1);
                    board.placePiece(currentPiece);
                }
                break;
            case "down":
                movePieceDown();
                break;
            case "up":
                Piece rotatedPiece = currentPiece.copy();
                rotatedPiece.rotate();
                if (board.canPlacePiece(rotatedPiece, rotatedPiece.getX(), rotatedPiece.getY())) {
                    board.removePiece(currentPiece);
                    currentPiece.rotate();
                    board.placePiece(currentPiece);
                }
                break;
        }
        if (showGame) {
            SwingUtilities.invokeLater(() -> gui.refresh());
        }
    }
    private void spawnNewPiece() {
        currentPiece = queue.getNextPiece();
        if (currentPiece != null) {
            // Set initial position at the top center of the board
            currentPiece.setX(4);  // Center horizontally (board width is 10)
            currentPiece.setY(0);  // Top of the board
            currentPiece.setInWait(false);
            currentPiece.setFalling(true);
            
            if (!board.canPlacePiece(currentPiece, currentPiece.getX(), currentPiece.getY())) {
                if(board.isGameOver()) {
                    gameOver();
                }
                //gameOver();
            } else {
                board.placePiece(currentPiece);
            }
        }
    }

    // Add these getter methods
    public Board getBoard() { return board; }
    public Queue getQueue() { return queue; }
    public Piece getCurrentPiece() { return currentPiece; }

    private void movePieceDown() {
        if (board.canPlacePiece(currentPiece, currentPiece.getX(), currentPiece.getY() + 1)) {
            board.removePiece(currentPiece);
            currentPiece.setY(currentPiece.getY() + 1);
            board.placePiece(currentPiece);
        } else {
            lockPiece();
        }
    }

    private void lockPiece() {
        currentPiece.setFalling(false);
        currentPiece.setPlaced(true);
        board.placePiece(currentPiece);
        board.clearLines();
        spawnNewPiece();
    }

    

    private void gameOver() {
        isGameOver = true;
        if (fallTimer != null) {
            fallTimer.cancel();
        }
        board.updateHighScore();
        if (showGame) {
            System.out.println("Game Over!");
        }
    }
        
    

    public boolean isGameOver() {
        return isGameOver;
    }

    public void stopGame() {
        if (fallTimer != null) {
            fallTimer.cancel();
        }
    }

    public void restartGame() {
        // Stop current game
        if (fallTimer != null) {
            fallTimer.cancel();
            fallTimer = null;
        }
        
        // Reset game components
        this.board = new Board();
        this.queue = new Queue(board);
        this.isGameOver = false;
        this.currentPiece = null;
        
        // Reinitialize game
        initializeGame();
        
        if (showGame) {
            SwingUtilities.invokeLater(() -> gui.refresh());
        }
    }

}