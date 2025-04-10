package com.tetris.game;

import java.util.LinkedList;
import java.util.Random;

public class Queue {
    private LinkedList<Piece> pieces;
    private Board board;
    private Random random;

    public Queue(Board board) {
        this.board = board;
        this.pieces = new LinkedList<>();
        this.random = new Random();
        // Initialize queue with some pieces
        for (int i = 0; i < 4; i++) {
            pieces.add(generateRandomPiece());
        }
    }

    public Piece getNextPiece() {
        if (pieces.isEmpty()) {
            return null;
        }
        
        // Get and remove the first piece
        Piece next = pieces.removeFirst();
        // Add a new piece to maintain queue size
        pieces.add(generateRandomPiece());
        
        // Set initial position for the piece (center top of board)
        next.setX(4);  // Center horizontally (assuming board width is 10)
        next.setY(0);  // Top of board
        
        return next;
    }

    public LinkedList<Piece> getNextThreePieces() {
        return new LinkedList<>(pieces.subList(0, Math.min(3, pieces.size())));
    }

    private Piece generateRandomPiece() {
        // Define all possible piece shapes
        int[][][] shapes = {
            // I piece
            {{1, 1, 1, 1}},
            // O piece
            {{1, 1},
             {1, 1}},
            // T piece
            {{0, 1, 0},
             {1, 1, 1}},
            // L piece
            {{1, 0},
             {1, 0},
             {1, 1}},
            // J piece
            {{0, 1},
             {0, 1},
             {1, 1}},
            // S piece
            {{0, 1, 1},
             {1, 1, 0}},
            // Z piece
            {{1, 1, 0},
             {0, 1, 1}}
        };
        
        // Random colors for pieces
        int[] colors = {
            0xFF0000, // Red
            0x00FF00, // Green
            0x0000FF, // Blue
            0xFFFF00, // Yellow
            0xFF00FF, // Magenta
            0x00FFFF, // Cyan
            0xFFA500  // Orange
        };

        int index = random.nextInt(shapes.length);
        return new Piece(shapes[index], colors[index]);
    }
}
