package tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        Scanner scanner = new Scanner(System.in);
        System.out.println(game.checkWin());
        while (!game.gameIsOver()) {
            game.printBoard();
            System.out.println("Enter your move:");
            int x = -1;
            int y = -1;
            do {
                try {
                    x = Integer.parseInt(scanner.nextLine());
                    y = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format!");
                }
                if (game.makeMove(x, y)) {
                    break;
                } else {
                    System.out.println("Invalid move!");
                }
            } while (!game.makeMove(x, y));
            if (game.checkWin() == 0) {
                game.printBoard();
                System.out.println("O won!");
            }
            if (game.checkWin() == 1) {
                game.printBoard();
                System.out.println("X won!");
            }
            if (game.checkWin() == 2) {
                game.printBoard();
                System.out.println("Draw!");
            }
        }
    }

}
