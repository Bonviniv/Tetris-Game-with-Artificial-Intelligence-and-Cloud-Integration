package com.tetris.game;

public class Piece {
    private int[][] shape;
    private int x;
    private int y;
    private boolean isFalling;
    private boolean isPlaced;
    private boolean inWait;
    private int color;

    public Piece(int[][] shape, int color) {
        this.shape = shape;
        this.color = color;
        this.isFalling = false;
        this.isPlaced = false;
        this.inWait = true;
        this.x = 0;
        this.y = 0;
    }

    public void rotate() {
        int[][] rotated = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }

    public Piece copy() {
        int[][] newShape = new int[shape.length][];
        for (int i = 0; i < shape.length; i++) {
            newShape[i] = shape[i].clone();
        }
        Piece copy = new Piece(newShape, color);
        copy.x = this.x;
        copy.y = this.y;
        copy.isFalling = this.isFalling;
        copy.isPlaced = this.isPlaced;
        copy.inWait = this.inWait;
        return copy;
    }

    // Getters and setters
    public int[][] getShape() { return shape; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isFalling() { return isFalling; }
    public boolean isPlaced() { return isPlaced; }
    public boolean isInWait() { return inWait; }
    public int getColor() { return color; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setFalling(boolean falling) { this.isFalling = falling; }
    public void setPlaced(boolean placed) { this.isPlaced = placed; }
    public void setInWait(boolean inWait) { this.inWait = inWait; }
}