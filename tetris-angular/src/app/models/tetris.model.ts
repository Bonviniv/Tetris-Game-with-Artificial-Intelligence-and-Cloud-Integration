export interface TetrisBlock {
    shape: number[][];
    color: string;
}

export interface GameBoard {
    width: number;
    height: number;
    grid: (TetrisBlock | null)[][];
}

export interface GameState {
    board: GameBoard;
    currentBlock: TetrisBlock;
    nextBlock: TetrisBlock;
    score: number;
    isGameOver: boolean;
}