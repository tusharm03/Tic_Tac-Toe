package com.csc;

import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (char) ('1' + (i * 3 + j)); // Fill with '1' to '9'
            }
        }
    }

    private void printBoard() {
        System.out.println(); // Add an extra line before printing the board
        for (int i = 0; i < 3; i++) {
            System.out.print(" ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
                if (j < 2) System.out.print("| ");
            }
            System.out.println();
            if (i < 2) System.out.println("-----------");
        }
        System.out.println(); // Add an extra line after printing the board
    }

    private boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell != 'X' && cell != 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
               (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.print("Player " + currentPlayer + " - where would you like to move? ");

            // Use a try-catch to handle potential input issues
            try {
                if (scanner.hasNextInt()) {
                    int move = scanner.nextInt() - 1; // Convert to 0-based index

                    // Validate the move
                    if (move < 0 || move > 8 || board[move / 3][move % 3] == 'X' || board[move / 3][move % 3] == 'O') {
                        System.out.println("That move is invalid!");
                        continue;
                    }

                    // Place the move on the board
                    board[move / 3][move % 3] = currentPlayer;

                    // Check for win or draw
                    if (checkWin()) {
                        printBoard();
                        System.out.println("Player " + currentPlayer + " wins!");
                        break;
                    }

                    if (isBoardFull()) {
                        printBoard();
                        System.out.println("It's a draw!");
                        break;
                    }

                    // Switch players
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            } catch (Exception e) {
                System.out.println("Error reading input: " + e.getMessage());
                scanner.next(); // Clear invalid input
            }
        }
        // Do not close the scanner, as it is reading System.in
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
