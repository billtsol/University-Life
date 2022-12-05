import java.util.ArrayList;

public abstract class Pieces {
    protected int coordinateX;
    protected int coordinateY;
    static final int PieceWidth = 25;
    static final int PieceHeigth = 25;

    private String imagePath;

    protected ArrayList<Pieces> enemies = new ArrayList<Pieces>();
    protected ArrayList<Pieces> team = new ArrayList<Pieces>();

    public Pieces(int coordinateX, int coordinateY, String imagePath) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.imagePath = imagePath;
    }

    public void setNewCoordinates(int x, int y) {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public int getCoordinateY() {
        return this.coordinateY;
    }

    public String getImagePath() {
        return this.imagePath;
    }

}
