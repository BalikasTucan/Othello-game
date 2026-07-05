/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

import java.util.ArrayList;

public class Board {
    public static final int SIZE = 8;
    private Pawn[][] board;

    public Board() {
        board = new Pawn[SIZE][SIZE];
        board[3][3] = new PawnWhite();
        board[3][4] = new PawnBlack();
        board[4][3] = new PawnBlack();
        board[4][4] = new PawnWhite();
    }

    public Pawn getPawnAt(int row, int col) {
        return board[row][col];
    }

    public void setPawnAt(int row, int col, Pawn pawn) {
        board[row][col] = pawn;
    }

    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public ArrayList<AvailableMove> getValidMoves(char currentPlayerSymbol) {
        ArrayList<AvailableMove> validMoves = new ArrayList<AvailableMove>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (getPawnAt(row, col) == null) {
                    boolean valid = false;
                    for (int dRow = -1; dRow <= 1; dRow++) {
                        for (int dCol = -1; dCol <= 1; dCol++) {
                            if (dRow == 0 && dCol == 0) continue;
                            int currentRow = row + dRow;
                            int currentCol = col + dCol;
                            boolean foundOpponent = false;
                            while (isInsideBoard(currentRow, currentCol)) {
                                Pawn currentPawn = getPawnAt(currentRow, currentCol);
                                if (currentPawn == null) break;
                                if (currentPawn.getSymbol() != currentPlayerSymbol) {
                                    foundOpponent = true;
                                } else {
                                    if (foundOpponent) valid = true;
                                    break;
                                }
                                currentRow += dRow;
                                currentCol += dCol;
                            }
                        }
                    }
                    if (valid) validMoves.add(new AvailableMove(row, col));
                }
            }
        }

        return validMoves;
    }

    public void makeMove(int row, int col, char currentPlayerSymbol) {
        if (currentPlayerSymbol == 'O') setPawnAt(row, col, new PawnBlack());
        else if (currentPlayerSymbol == 'X') setPawnAt(row, col, new PawnWhite());

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) continue;
                flipDirection(row, col, dRow, dCol, currentPlayerSymbol);
            }
        }
    }

    public void flipDirection(int row, int col, int dRow, int dCol, char currentPlayerSymbol) {
        int currentRow = row + dRow;
        int currentCol = col + dCol;
        ArrayList<int[]> toFlip = new ArrayList<int[]>();

        while (isInsideBoard(currentRow, currentCol)) {
            Pawn currentPawn = getPawnAt(currentRow, currentCol);
            if (currentPawn == null) return;
            if (currentPawn.getSymbol() != currentPlayerSymbol) {
                toFlip.add(new int[]{currentRow, currentCol});
            } else {
                for (int[] pos : toFlip) {
                    if (currentPlayerSymbol == 'O') setPawnAt(pos[0], pos[1], new PawnBlack());
                    else setPawnAt(pos[0], pos[1], new PawnWhite());
                }
                return;
            }
            currentRow += dRow;
            currentCol += dCol;
        }
    }

    public int evaluate(char aiSymbol) {
        int totalScore = 0;
        int[][] weights = {
            { 500, -20, 10, 5, 5, 10, -20, 500 },
            { -20, -50, -2, -2, -2, -2, -50, -20 },
            { 10, -2, -1, -1, -1, -1, -2, 10 },
            { 5, -2, -1, -1, -1, -1, -2, 5 },
            { 5, -2, -1, -1, -1, -1, -2, 5 },
            { 10, -2, -1, -1, -1, -1, -2, 10 },
            { -20, -50, -2, -2, -2, -2, -50, -20 },
            { 500, -20, 10, 5, 5, 10, -20, 500 }
        };

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Pawn p = getPawnAt(row, col);
                if (p != null) {
                    if (p.getSymbol() == aiSymbol) totalScore += weights[row][col];
                    else totalScore -= weights[row][col];
                }
            }
        }

        return totalScore;
    }

    public void printBoard(ArrayList<AvailableMove> highlightedMoves) {
        System.out.print("  a b c d e f g h\n");
        for (int row = 0; row < SIZE; row++) {
            System.out.print((row + 1));
            for (int col = 0; col < SIZE; col++) {
                Pawn currentPawn = getPawnAt(row, col);
                if (currentPawn != null) System.out.print(" " + currentPawn.getSymbol());
                else {
                    boolean highlight = false;
                    if (highlightedMoves != null) {
                        for (AvailableMove move : highlightedMoves) {
                            if (move.row == row && move.col == col) highlight = true;
                        }
                    }
                    if (highlight) System.out.print(" *");
                    else System.out.print(" .");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
    

