package tictactoe;

public class TicTacToeBot {

    private final TicTacToeGame game;
    private final int depth;

    public TicTacToeBot(TicTacToeGame game, int depth) {
        this.depth = depth;
        this.game = game;
    }

    public void makeMove() {
        if (!game.gameIsOver()) {
            int bestScore = game.turn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            int bestMoveIndex = -1;
            boolean botTurn = game.turn;

            for (int index : Utility.getAllEmptySquare(game.getBoard())) {
                int[] move = Utility.indexToCoordinates(index);
                TicTacToeGame afterMove = Utility.newGameFromArgs(botTurn, game.makeMoveNewBoard(move[0], move[1]));
                afterMove.turn = !afterMove.turn;
                int score = minimax(afterMove, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, afterMove.turn);
                if (botTurn && score > bestScore) {
                    bestScore = score;
                    bestMoveIndex = index;
                } else if (!botTurn && score < bestScore) {
                    bestScore = score;
                    bestMoveIndex = index;
                }
            }

            if (bestMoveIndex != -1) {
                int[] bestMove = Utility.indexToCoordinates(bestMoveIndex);
                game.makeMove(bestMove[0], bestMove[1]);
            }
        }
    }

    public static int minimax(TicTacToeGame currentGame, int alpha, int beta, int depth, boolean turn) {
        if (depth == 0 || currentGame.gameIsOver()) {
            return currentGame.checkWin();
        }

        if (turn) {
            int maxEval = Integer.MIN_VALUE;
            int eval;
            for (int index : Utility.getAllEmptySquare(currentGame.getBoard())) {
                int[] move = Utility.indexToCoordinates(index);
                TicTacToeGame afterMove = Utility.newGameFromArgs(currentGame.turn, currentGame.makeMoveNewBoard(move[0], move[1]));
                afterMove.turn = !afterMove.turn;
                eval = minimax(afterMove, alpha, beta, depth - 1, afterMove.turn);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, maxEval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            int eval;
            for (int index : Utility.getAllEmptySquare(currentGame.getBoard())) {
                int[] move = Utility.indexToCoordinates(index);
                TicTacToeGame afterMove = Utility.newGameFromArgs(currentGame.turn, currentGame.makeMoveNewBoard(move[0], move[1]));
                afterMove.turn = !afterMove.turn;
                eval = minimax(afterMove, alpha, beta, depth - 1, afterMove.turn);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, minEval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }


}
