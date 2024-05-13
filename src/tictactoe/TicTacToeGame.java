package tictactoe;

import java.util.Random;

public class TicTacToeGame {

    public boolean turn;
    private int[][] board;
    public final int width;
    public final int height;
    public final Random random = new Random();

    public TicTacToeGame() {
        turn = random.nextBoolean();
        width = 3;
        height = 3;
        initializeBoard();
    }

    public TicTacToeGame(boolean turn, int[][] startingBoard) {
        width = 3;
        height = 3;
        this.turn = turn;
        board = startingBoard;
    }

    private void setSquare(int posX, int posY, int xO) {
        board[posX][posY] = xO;
    }

    public int getSquare(int posX, int posY) {
        return board[posX][posY];
    }

    public int[][] getBoard() {
        return board;
    }

    private void initializeBoard() {
        board = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
            }
        }
    }

    public boolean withinBoard(int posX, int posY) {
        return posX >= 0 && posX < height && posY >= 0 && posY <= width;
    }

    public boolean makeMove(int posX, int posY) {
        if (withinBoard(posX, posY) && getSquare(posX, posY) == 0) {
            setSquare(posX, posY, !turn ? -1 : 1);
            turn = !turn;
            SFX.playSound("/asset/beep.wav", 5);
            return true;
        }
        return false;
    }

    public int[][] makeMoveNewBoard(int posX, int posY) {
        int[][] newBoard = Utility.copyBoard(board);
        if (withinBoard(posX, posY) && getSquare(posX, posY) == 0) {
            newBoard[posX][posY] = !turn ? -1 : 1;
        }
        return newBoard;
    }

    // -2 for no winner YET, -1 for O, 1 for X, 0 for draw
    // This is ONLY for tictactoe, which only requires checking for 2 main diagonals
    public int checkWin() {
        // Check cols
        for (int i = 0; i < width; i++) {
            boolean colWin = true;
            for (int j = 0; j < height - 1; j++) {
                if (board[j][i] != board[j + 1][i] || board[j][i] == 0 || board[j + 1][i] == 0) {
                    colWin = false;
                    break;
                }
            }
            if (colWin && board[0][i] != 0) {
                return board[0][i];
            }
        }

        // Check rows
        for (int i = 0; i < height; i++) {
            boolean rowWin = true;
            for (int j = 0; j < width - 1; j++) {
                if (board[i][j] != board[i][j + 1] || board[i][j] == 0 || board[i][j + 1] == 0) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin && board[i][0] != 0) {
                return board[i][0];
            }
        }

        // Check main diagonal
        boolean mainDiagWin = true;
        for (int i = 0; i < height - 1; i++) {
            if (board[i][i] != board[i + 1][i + 1] || board[i][i] == 0 || board[i + 1][i + 1] == 0){
                mainDiagWin = false;
                break;
            }
        }
        if (mainDiagWin && board[0][0] != 0) {
            return board[0][0];
        }

        // Check second diagonal
        boolean secondDiagWin = true;
        for (int i = 0; i < height - 1; i++) {
            if (board[i][height - i - 1] != board[i + 1][height - i - 2] ||
                    board[i][height - i - 1] == 0 || board[i + 1][height - i - 2] == 0) {
                secondDiagWin = false;
                break;
            }
        }
        if (secondDiagWin && board[0][height - 1] != 0) {
            return board[0][height - 1];
        }

        // Check draw
        boolean draw = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 0) {
                    draw = false;
                    break;
                }
            }
            if (!draw) {
                break;
            }
        }
        if (draw) {
            return 0;
        }

        return -2;
    }

    public boolean gameIsOver() {
        return checkWin() != -2;
    }

    public void printBoard() {
        System.out.println("----".repeat(width) + "-");
        for (int i = 0; i < height; i++) {
            System.out.print("| ");
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 0) {
                    System.out.print(" ");
                } else if (board[i][j] == -1) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
                System.out.print(" | ");
            }
            System.out.println();
            System.out.println("----".repeat(width) + "-");
        }
    }

}
