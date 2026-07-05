/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

import java.util.*;

public class HW2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();

        char playerSymbol;
        char aiSymbol;
        int depth;

       
        System.out.println("Welcome to Othello!\n");

    
        while (true) {
            System.out.print("Choose color (black/white/b/w/B/W/O/X/o/x): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("black") || input.equals("b") || input.equals("o")) {
                playerSymbol = 'O';
                aiSymbol = 'X';
                break;
            } else if (input.equals("white") || input.equals("w") || input.equals("x")) {
                playerSymbol = 'X';
                aiSymbol = 'O';
                break;
            } else {
                System.out.println("Invalid color. Try again...");
            }
        }

     
        while (true) {
            System.out.print("Estimate forward moves [1,9]: ");
            try {
                depth = Integer.parseInt(scanner.nextLine().trim());
                if (depth >= 1 && depth <= 9) {
                    break;
                } else {
                    System.out.println("Invalid moves. Try again...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid moves. Try again...");
            }
        }

        AIPlayer ai = new AIPlayer();
        ArrayList<String> moveHistory = new ArrayList<String>();

        char currentPlayer = 'O';

        while (true) {
            ArrayList<AvailableMove> validMoves = board.getValidMoves(currentPlayer);

            if (board.getValidMoves('O').size() == 0 && board.getValidMoves('X').size() == 0) {
                break;
            }

            System.out.println("Player " + currentPlayer + " turn\n");

            if (validMoves.size() == 0) {
                board.printBoard(null);
                System.out.println("No available moves!\n");
                currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
                continue;
            }

            board.printBoard(validMoves);

            if (currentPlayer == playerSymbol) {
                while (true) {
                    System.out.print("Enter your move (e.g. c2): ");
                    String moveStr = scanner.nextLine().trim().toLowerCase();

                    if (moveStr.length() >= 2) {
                        char colChar = moveStr.charAt(0);
                        int rowNum = Character.getNumericValue(moveStr.charAt(1));
                        int row = rowNum - 1;
                        int col = colChar - 'a';

                        boolean valid = false;
                        for (AvailableMove m : validMoves) {
                            if (m.row == row && m.col == col) {
                                valid = true;
                            }
                        }

                        if (valid) {
                            board.makeMove(row, col, playerSymbol);
                            moveHistory.add(moveStr);
                            break;
                        } else {
                            System.out.println("Invalid move. Try again!");
                        }
                    } else {
                        System.out.println("Invalid move. Try again!");
                    }
                }
            } else {
                AvailableMove bestMove = ai.findBestMove(board, depth, aiSymbol, playerSymbol);
                if (bestMove != null) {
                    board.makeMove(bestMove.row, bestMove.col, aiSymbol);
                    System.out.println("Player " + aiSymbol + " played: " + bestMove.toString());
                    moveHistory.add(bestMove.toString());
                }
            }

            board.printBoard(null);
            System.out.print("Moves history: ");
            for (String move : moveHistory) {
                System.out.print(move + " ");
            }
            System.out.println("\n");

            currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
        }

     
        int countX = 0;
        int countO = 0;

        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                Pawn p = board.getPawnAt(i, j);
                if (p != null) {
                    if (p.getSymbol() == 'X') {
                        countX++;
                    } else {
                        countO++;
                    }
                }
            }
        }

        System.out.println("X:" + countX + "/O:" + countO);

        if (countX > countO) {
            System.out.println("The Player X won!");
        } else if (countO > countX) {
            System.out.println("The Player O won!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static Board boardCopy(Board original) {
        Board newBoard = new Board();

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Pawn p = original.getPawnAt(row, col);
                if (p != null) {
                    if (p.getSymbol() == 'O') {
                        newBoard.setPawnAt(row, col, new PawnBlack());
                    } else {
                        newBoard.setPawnAt(row, col, new PawnWhite());
                    }
                }
            }
        }

        return newBoard;
    }
}
    
