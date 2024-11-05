package com.csc;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private Scanner scanner;
    private Random random;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        scanner = new Scanner(System.in);
        random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (char) ('1' + (i * 3 + j));
            }
        }
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.print(" ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
                if (j < 2) System.out.print("| ");
            }
            System.out.println();
            if (i < 2) System.out.println("-----------");
        }
        System.out.println();
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
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
               (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    public void playHumanVsHuman() {
        while (true) {
            printBoard();
            System.out.print("Player " + currentPlayer + " - where would you like to move? ");
            makeMove();
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
            switchPlayer();
        }
    }

    public void playHumanVsComputer() {
        while (true) {
            printBoard();
            if (currentPlayer == 'X') {
                System.out.print("Player " + currentPlayer + " - where would you like to move? ");
                makeMove();
                if (checkWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }
            } else {
                makeRandomMove();
                System.out.println("The computer player moved in space " + getLastMove());
                if (checkWin()) {
                    printBoard();
                    System.out.println("The computer player wins!");
                    break;
                }
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            switchPlayer();
        }
    }

    private void makeMove() {
        while (true) {
            try {
                int move = scanner.nextInt() - 1; 
                if (move < 0 || move > 8 || board[move / 3][move % 3] == 'X' || board[move / 3][move % 3] == 'O') {
                    System.out.println("That is not a valid choice!");
                    continue;
                }
                board[move / 3][move % 3] = currentPlayer;
                break;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); 
            }
        }
    }

    private void makeRandomMove() {
        int move;
        do {
            move = random.nextInt(9);
        } while (board[move / 3][move % 3] == 'X' || board[move / 3][move % 3] == 'O');
        board[move / 3][move % 3] = currentPlayer;
    }

    private int getLastMove() {
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == currentPlayer) {
                return i + 1; // Return 1-based index
            }
        }
        return -1; // Should never happen
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void resetGame() {
        initializeBoard();
        currentPlayer = 'X';
    }

    private boolean askToPlayAgain() {
        System.out.print("Would you like to play again? (1) Yes (2) No: ");
        while (true) {
            try {
                int answer = scanner.nextInt();
                if (answer == 1) {
                    resetGame();
                    return true;
                } else if (answer == 2) {
                    System.out.println("Goodbye!");
                    return false;
                } else {
                    System.out.println("\nThat is not a valid choice!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }
        }
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        while (true) {
            System.out.println("Welcome to Tic-Tac-Toe!");
            System.out.println("Please choose a game mode:");
            System.out.println("(1) Human vs. human");
            System.out.println("(2) Human vs. computer");

            try {
                int choice = game.scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Let's begin!");
                        game.playHumanVsHuman();
                        break;
                    case 2:
                        System.out.println("Let's begin!");
                        game.playHumanVsComputer();
                        break;
                    default:
                        System.out.println("\nThat is not a valid choice!");
                        continue;
                }

                if (!game.askToPlayAgain()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                game.scanner.next(); // Clear invalid input
            }
        }
    }
}
