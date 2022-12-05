import java.util.ArrayList;

public class Player {
    private int color;

    ArrayList<Pieces> pieces = new ArrayList<Pieces>();

    public Player(int color) {
        pieces.add(new Rook(0, 0, ""));
        pieces.add(new Knight(0, 0, ""));
        pieces.add(new Bishop(0, 0, ""));

        pieces.add(new King(0, 0, ""));
        pieces.add(new Queen(0, 0, ""));
        

        pieces.add(new Bishop(0, 0, ""));
        pieces.add(new Knight(0, 0, ""));
        pieces.add(new Knight(0, 0, ""));
        pieces.add(new Knight(0, 0, ""));
        this.color = color;

    }
}
