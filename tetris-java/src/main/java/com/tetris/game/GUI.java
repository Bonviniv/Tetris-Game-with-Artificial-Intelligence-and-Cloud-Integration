package com.tetris.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI extends JFrame {
    private GamePanel gamePanel;
    private GameEngine gameEngine;
    private static final int BLOCK_SIZE = 30;
    private static final int PADDING = 10;
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final int BOARD_X = 50;
    private static final int BOARD_Y = 50;

    public GUI(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gamePanel = new GamePanel();
        setupFrame();
        setupKeyListener();
    }

    private JButton restartButton;

    private void setupFrame() {
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Create restart button
        restartButton = new JButton("Restart");
        restartButton.setFocusable(false); // Prevent button from taking keyboard focus
        restartButton.addActionListener(e -> {
            gameEngine.restartGame();
            requestFocusInWindow(); // Return focus to main window for key events
        });
        
        // Add components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(restartButton, BorderLayout.SOUTH);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        gameEngine.handleInput("left");
                        break;
                    case KeyEvent.VK_RIGHT:
                        gameEngine.handleInput("right");
                        break;
                    case KeyEvent.VK_DOWN:
                        gameEngine.handleInput("down");
                        break;
                    case KeyEvent.VK_UP:
                        gameEngine.handleInput("up");
                        break;
                }
                gamePanel.repaint();
            }
        });
        setFocusable(true);
    }

    public void refresh() {
        gamePanel.repaint();
    }

    private class GamePanel extends JPanel {
        public GamePanel() {
            setPreferredSize(new Dimension(BLOCK_SIZE * 16, BLOCK_SIZE * 20));
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard(g);
            drawNextPieces(g);
            drawScore(g);
        }

        private void drawScore(Graphics g) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            
            // Draw current score
            String scoreText = "Score: " + gameEngine.getBoard().getScore();
            g.drawString(scoreText, 11 * BLOCK_SIZE, 15 * BLOCK_SIZE);
            
            // Draw high score
            String highScoreText = "High Score: " + gameEngine.getBoard().getHighScore();
            g.drawString(highScoreText, 11 * BLOCK_SIZE, 17 * BLOCK_SIZE);
        }

        private void drawBoard(Graphics g) {
            Board board = gameEngine.getBoard();
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 10; x++) {
                    drawCell(g, x * BLOCK_SIZE + PADDING, y * BLOCK_SIZE + PADDING, 
                            board.getCellState(x, y));
                }
            }
        }

        private void drawNextPieces(Graphics g) {
            int startX = 11 * BLOCK_SIZE;
            int startY = PADDING;
            g.setColor(Color.WHITE);
           
            if(!gameEngine.getBoard().isGameOver()){
                g.drawString("Next Pieces:", startX, startY);
            }else{
                g.drawString("Game Over!", startX, startY);
            }
            
            Queue queue = gameEngine.getQueue();
            int yOffset = BLOCK_SIZE;
            for (Piece piece : queue.getNextThreePieces()) {
                drawPiece(g, piece, startX, startY + yOffset);
                yOffset += 4 * BLOCK_SIZE;
            }
        }

        private void drawPiece(Graphics g, Piece piece, int x, int y) {
            int[][] shape = piece.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] == 1) {
                        g.setColor(new Color(piece.getColor()));
                        g.fillRect(x + j * BLOCK_SIZE, y + i * BLOCK_SIZE, 
                                BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    }
                }
            }
        }

        private void drawCell(Graphics g, int x, int y, Board.CellState state) {
            switch (state) {
                case EMPTY:
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x, y, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    break;
                case ACTIVE:
                    g.setColor(new Color(gameEngine.getCurrentPiece().getColor()));
                    g.fillRect(x, y, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    break;
                case FIXED:
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    break;
                case GHOST:
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x, y, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    break;
            }
        }
    }
}