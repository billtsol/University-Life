import java.util.*;

public class Board {
    private int gameEnd = 0;

    // Βοηθητικη λιστα για ολες τις διαθεσιμες κινησεις. Ο υπολογιστης επιλεγει απο
    // εδω με την πρωτη
    private ArrayList<String> correctInputs = new ArrayList<String>(
            Arrays.asList("A1", "B1", "C1", "A2", "B2", "C2", "A3", "B3", "C3"));

    private char[][] gameBoard = {
            { ' ', ' ', ' ' },
            { ' ', ' ', ' ' },
            { ' ', ' ', ' ' }
    };
    // Πινακες με τις τιμες για τον ευρεση του νικητη
    private int[] boardRows = { 0, 0, 0 };
    private int[] boardCol = { 0, 0, 0 };
    private int[] boardDiag = { 0, 0 };

    private int positionNumber = 0;

    public Board() {
        System.out.println("************");
        System.out.println("Tic-Tac-Toe!");
        System.out.println("************");
        System.out.println("");
        System.out.println("Please enter the column (A, B or C) and then the row (1, 2 or 3) of your move.");
        System.out.println("");
    }

    public int get_gameEnd() {
        if (this.positionNumber == 9 && this.gameEnd == 0) {
            this.gameEnd = -1;
        }
        return this.gameEnd;
    }

    // ελεχγος της τιμης που εδωσε ο χρηστης, περνουντας και την τιμη Χ 'η Ο η
    // συναρτηση θα υλοποιουσε και παιχνιδι παιχτη vs παιχτη
    public boolean isCorrectPosition(String inputString) {

        int ascii = (int) inputString.charAt(0) - 65;
        int num = Character.getNumericValue(inputString.charAt(1)) - 1;

        if (ascii < 0 || ascii > 2 || num < 0 || num > 2 || inputString.length() != 2) {
            System.out
                    .println("Invalid Input: Please enter the column and row of your move (Example: A1).");
            return false;
        }
        if (isFreeMove(inputString)) {
            if (isWinner(num, ascii, 1)) {
                this.gameEnd = 1;
            }
            this.gameBoard[num][ascii] = 'X';// αλλαγη του ταμπλο
            this.positionNumber++;
            return true;
        }

        System.out.println("The space entered is already taken");

        return false;
    }

    public boolean isFreeMove(String move) {
        return this.correctInputs.remove(move);
    }

    // Υπολογιστης
    public String computerMove() {
        int random_int = (int) Math.floor(Math.random() * (correctInputs.size()));
        String move = correctInputs.get(random_int);
        isFreeMove(move); // σιγουρα ειναι σωστη η επιλογη με την πρωτη φορα
        int ascii = (int) move.charAt(0) - 65;
        int num = Character.getNumericValue(move.charAt(1)) - 1;
        this.gameBoard[num][ascii] = 'O'; // αλλαγη του ταμπλο
        this.positionNumber++;
        if (isWinner(num, ascii, 4)) {
            this.gameEnd = 2;
        }
        return move;
    }

    public boolean isWinner(int row, int col, int playerCode) {
        this.boardRows[col] += playerCode;
        this.boardCol[row] += playerCode;

        if (col == row) {
            boardDiag[0] += playerCode;
        } else if (row + col + 2 == 3) {
            boardDiag[1] += playerCode;
        }

        if (this.boardRows[col] == playerCode * 3) {
            return true;
        } else if (boardCol[row] == playerCode * 3) {
            return true;
        }

        return boardDiag[0] == playerCode * 3 || boardDiag[1] == playerCode * 3;
    }

    public void printBoard() {
        System.out.printf("%6c%4c%4c\n%c%3c%2c%2c%2c%2c%2c%2c\n%c%3c%2c%2c%2c%2c%2c%2c\n%c%3c%2c%2c%2c%2c%2c%2c\n",
                'A', 'B', 'C', '1', '|', this.gameBoard[0][0], '|', this.gameBoard[0][1], '|', this.gameBoard[0][2],
                '|', '2', '|', this.gameBoard[1][0], '|', this.gameBoard[1][1], '|', this.gameBoard[1][2], '|', '3',
                '|', this.gameBoard[2][0], '|', this.gameBoard[2][1], '|', this.gameBoard[2][2], '|');
    }
}