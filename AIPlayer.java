/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

import java.util.*;

public class AIPlayer {
    public AvailableMove findBestMove(Board board, int depth, char aiSymbol, char opponentSymbol) {
        ArrayList<AvailableMove> moves = board.getValidMoves(aiSymbol);
        int bestValue = Integer.MIN_VALUE;
        AvailableMove bestMove = null;

        for (AvailableMove move : moves) {
            Board newBoard = HW2.boardCopy(board);
            newBoard.makeMove(move.row, move.col, aiSymbol);
            int moveValue = minimax(newBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, aiSymbol, opponentSymbol);
            if (moveValue > bestValue || bestMove == null) {
                bestValue = moveValue;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, char aiSymbol, char opponentSymbol) {
        if (depth == 0) return board.evaluate(aiSymbol);

        ArrayList<AvailableMove> moves = maximizingPlayer ? board.getValidMoves(aiSymbol) : board.getValidMoves(opponentSymbol);
        if (moves.size() == 0) return board.evaluate(aiSymbol);

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (AvailableMove move : moves) {
                Board newBoard = HW2.boardCopy(board);
                newBoard.makeMove(move.row, move.col, aiSymbol);
                int eval = minimax(newBoard, depth - 1, alpha, beta, false, aiSymbol, opponentSymbol);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (AvailableMove move : moves) {
                Board newBoard = HW2.boardCopy(board);
                newBoard.makeMove(move.row, move.col, opponentSymbol);
                int eval = minimax(newBoard, depth - 1, alpha, beta, true, aiSymbol, opponentSymbol);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }
}
    

