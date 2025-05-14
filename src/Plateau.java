
import java.util.List;
import java.util.Observable;

public abstract class Plateau extends Observable {
    protected Piece[][] cases;
    protected int axeX;
    protected int axeY;
    protected boolean damier;

    public abstract void initialiserGrille();

    public abstract void placerPiece(Piece piece, int x, int y);

    public abstract void relationVue();

    public abstract boolean estOccupe(Position p);
    

    public int getAxeX() {
        return axeX;
    }

    public int getAxeY() {
        return axeY;
    }

    public boolean isDamier() {
        return damier;
    }

}

