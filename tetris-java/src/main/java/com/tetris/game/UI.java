package com.tetris.game;

public class UI {
    private static final String EMPTY_CELL = "□";
    private static final String ACTIVE_CELL = "■";
    private static final String FIXED_CELL = "▣";
    private static final String BORDER = "║";
    private static final String BOTTOM = "═";
    private static final String CORNER = "╚";
    
    public static void displayGame(Board board, Queue queue, Piece currentPiece) {
        clearScreen();
        displayBoard(board, currentPiece);
        displayNextPieces(queue);
    }
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    private static void displayBoard(Board board, Piece currentPiece) {
        System.out.println("\nTetris Game Board:");
        
        // Display the board with current piece
        for (int i = 0; i < 20; i++) {
            System.out.print(BORDER + " ");
            for (int j = 0; j < 10; j++) {
                if (isCurrentPieceLocation(currentPiece, j, i)) {
                    System.out.print(ACTIVE_CELL + " ");
                } else {
                    Board.CellState cell = board.getCellState(j, i);
                    switch (cell) {
                        case EMPTY:
                            System.out.print(EMPTY_CELL + " ");
                            break;
                        case FIXED:
                            System.out.print(FIXED_CELL + " ");
                            break;
                        default:
                            System.out.print(EMPTY_CELL + " ");
                    }
                }
            }
            System.out.println(BORDER);
        }
        
        // Display bottom border
        System.out.print(CORNER);
        for (int i = 0; i < 21; i++) {
            System.out.print(BOTTOM);
        }
        System.out.println();
    }
    
    private static void displayNextPieces(Queue queue) {
        System.out.println("\nNext Pieces:");
        int pieceNumber = 1;
        for (Piece piece : queue.getNextThreePieces()) {
            System.out.println("\nPiece " + pieceNumber + ":");
            displayPiecePreview(piece);
            pieceNumber++;
        }
    }
    
    private static void displayPiecePreview(Piece piece) {
        int[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            System.out.print("    ");
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    System.out.print(ACTIVE_CELL + " ");
                } else {
                    System.out.print(EMPTY_CELL + " ");
                }
            }
            System.out.println();
        }
    }
    
    private static boolean isCurrentPieceLocation(Piece piece, int x, int y) {
        if (piece == null || !piece.isFalling()) {
            return false;
        }
        
        int[][] shape = piece.getShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1 && 
                    x == pieceX + j && 
                    y == pieceY + i) {
                    return true;
                }
            }
        }
        return false;
    }
}