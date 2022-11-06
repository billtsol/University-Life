
/*
 * Ασκηση 1 JAVA
 * Τσολακίδης Βασιλης
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board game1;
        boolean player = true;
        Scanner in = new Scanner(System.in);
        String inputString;

        game1 = new Board(); // Δημιουργια το αντικειμενου board το οποιο χειριζεται το παιχνιδι.
        game1.printBoard();

        int stop = game1.get_gameEnd(); // Μεταβλητη ελεγχου του αντικειμενουν board που δειχνει τον νικητη και τον
                                        // τερματισμο του παιχνιδου

        while (stop == 0) {
            if (player) {
                do {
                    System.out.printf("\nPlayer Move (X): ");
                    inputString = in.nextLine(); // Διαβασμα του χρηστη
                    System.out.println("");
                } while (!game1.isCorrectPosition(inputString));// Ελεγχεις τις τιμες του χρηστη.
                player = false;
            } else {
                System.out.println("\nComputer Move (0): " + game1.computerMove()); // Επιλογη του υπολογιστη
                System.out.println("");
                player = true;
            }
            // Εμφανιση του ταμπλο
            game1.printBoard();
            stop = game1.get_gameEnd();
        }
        // επιλογη νικητη
        if (stop == 1) {
            System.out.println("\nYou win!");
        } else if (stop == 2) {
            System.out.println("\nYou lose!");
        } else {
            System.out.println("\nDraw");
        }

        in.close();
    }
}