package tictactoe;

import java.util.Arrays;

public class Utility {

    public static int[][] copyBoard(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[][] copy = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    public static int[] getAllEmptySquare(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[] emptySquare = new int[9];
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    emptySquare[count++] = i * 3 + j;
                }
            }
        }
        return Arrays.copyOf(emptySquare, count);
    }

    public static TicTacToeGame newGameFromArgs(boolean turn, int[][] board) {
        return new TicTacToeGame(turn, board);
    }

    public static int coordinatesToIndex(int posX, int posY) {
        return posX * 3 + posY;
    }

    public static int[] indexToCoordinates(int index) {
        return new int[]{index / 3, index % 3};
    }

}
