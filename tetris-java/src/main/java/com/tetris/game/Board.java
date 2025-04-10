package com.tetris.game;

public class Board {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private CellState[][] grid;

    public enum CellState {
        EMPTY,
        ACTIVE,
        FIXED,
        GHOST  // Add ghost state
    }

    private static int highScore = 0;  // Static to persist between games
    private int score;

    public Board() {
        grid = new CellState[HEIGHT][WIDTH];
        score = 0;
        // Initialize all cells as empty
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = CellState.EMPTY;
            }
        }
    }

    public boolean hasHighCollision(Piece piece) {
        if (piece == null) return false;
        
        int[][] shape = piece.getShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardY = pieceY + i;
                    int boardX = pieceX + j;
                    
                    if (boardY >= 10 && isWithinBoundaries(boardX, boardY)) {
                        if (grid[boardY][boardX] == CellState.FIXED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

       // Add after getCellState method
       public void placePiece(Piece piece) {
        if (piece == null) return;
        
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        
        if (canPlacePiece(piece, pieceX, pieceY)) {
            int[][] shape = piece.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] == 1) {
                        int boardX = pieceX + j;
                        int boardY = pieceY + i;
                        if (isWithinBoundaries(boardX, boardY)) {
                            grid[boardY][boardX] = piece.isPlaced() ? CellState.FIXED : CellState.ACTIVE;
                        }
                    }
                }
            }
            
            // Update ghost piece if piece is active
            if (!piece.isPlaced() && piece.isFalling()) {
                updateGhostPiece(piece);
            }
        }
    }

    // Modify removePiece to also remove ghost
    public void removePiece(Piece piece) {
        if (piece == null) return;
        
        removeGhostPiece(piece);
        
        int[][] shape = piece.getShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardX = pieceX + j;
                    int boardY = pieceY + i;
                    if (isWithinBoundaries(boardX, boardY)) {
                        grid[boardY][boardX] = CellState.EMPTY;
                    }
                }
            }
        }
    }

    public boolean isGameOver() {
        // Check if any piece is fixed in the top row
        for (int x = 0; x < WIDTH; x++) {
            if (grid[0][x] == CellState.FIXED) {
                return true;
            }
        }
        return false;
    }

    public CellState getCellState(int x, int y) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            return grid[y][x];
        }
        return CellState.EMPTY;
    }

    public boolean canPlacePiece(Piece piece, int x, int y) {
        if (piece == null) return false;
        
        int[][] shape = piece.getShape();
        int rows = shape.length;
        int cols = shape[0].length;
        
        // Check boundaries and collisions
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (shape[i][j] == 1) {
                    int boardX = x + j;
                    int boardY = y + i;
                    
                    if (!isWithinBoundaries(boardX, boardY)) {
                        return false;
                    }
                    
                    // Only check for fixed pieces, allow active pieces to move
                    if (grid[boardY][boardX] == CellState.FIXED) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean hasSupportBelow(Piece piece, int x, int y) {
        if (piece == null) return false;
        
        int[][] shape = piece.getShape();
        
        // Check if piece has reached bottom or has a fixed piece below
        for (int j = 0; j < shape[0].length; j++) {
            for (int i = shape.length - 1; i >= 0; i--) {
                if (shape[i][j] == 1) {
                    int boardY = y + i;
                    int boardX = x + j;
                    
                    // Check if piece reached bottom or has fixed piece below
                    if (boardY + 1 >= HEIGHT || 
                        (boardY + 1 < HEIGHT && grid[boardY + 1][boardX] == CellState.FIXED)) {
                        return true;
                    }
                    break; // Move to next column after finding lowest piece
                }
            }
        }
        return false;
    }

    private boolean checkPieceCells(Piece piece, int x, int y) {
        return canPlacePiece(piece, x, y);
    }

    private boolean isWithinBoundaries(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private boolean isOccupied(int x, int y) {
        return grid[y][x] == CellState.FIXED;
    }


        public int clearLines() {
            int linesCleared = 0;
            
            for (int row = HEIGHT - 1; row >= 0; row--) {
                if (isLineComplete(row)) {
                    clearLine(row);
                    shiftLinesDown(row);
                    linesCleared++;
                    row++; // Recheck the same row after shifting
                }
            }
            
            // Update score based on lines cleared
            switch (linesCleared) {
                case 1:
                    score += 100;
                    break;
                case 2:
                    score += 300;
                    break;
                case 3:
                    score += 500;
                    break;
                case 4:
                    score += 800;
                    break;
            }
            
            return linesCleared;
        }
    
        private boolean isLineComplete(int row) {
            for (int col = 0; col < WIDTH; col++) {
                if (grid[row][col] != CellState.FIXED) {
                    return false;
                }
            }
            return true;
        }
    
        private void clearLine(int row) {
            for (int col = 0; col < WIDTH; col++) {
                grid[row][col] = CellState.EMPTY;
            }
        }
    
        private void shiftLinesDown(int clearedRow) {
            for (int row = clearedRow - 1; row >= 0; row--) {
                for (int col = 0; col < WIDTH; col++) {
                    grid[row + 1][col] = grid[row][col];
                    grid[row][col] = CellState.EMPTY;
                }
            }
        }
    

    public void updateGhostPiece(Piece piece) {
        if (piece == null || !piece.isFalling()) return;
        
        // Remove old ghost piece if exists
        removeGhostPiece(piece);
        
        // Find the lowest possible position
        int ghostY = piece.getY();
        while (canPlacePiece(piece, piece.getX(), ghostY + 1)) {
            ghostY++;
        }
        
        // Place ghost piece
        int[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardX = piece.getX() + j;
                    int boardY = ghostY + i;
                    if (isWithinBoundaries(boardX, boardY) && 
                        grid[boardY][boardX] == CellState.EMPTY) {
                        grid[boardY][boardX] = CellState.GHOST;
                    }
                }
            }
        }
    }

    private void removeGhostPiece(Piece piece) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] == CellState.GHOST) {
                    grid[y][x] = CellState.EMPTY;
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }
}
