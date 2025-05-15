
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

    public boolean estOccupe(Position p) {
        return getPiece(p.x, p.y) != null;
    }


    public boolean estOccupeParAllie(Position p, boolean estBlanc) {
        Piece cible = getPiece(p.x, p.y);
        return cible != null && cible.estBlanche() == estBlanc;
    }

    public int getAxeX() {
        return axeX;
    }

    public int getAxeY() {
        return axeY;
    }

    public boolean isDamier() {
        return damier;
    }

    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= axeX || y < 0 || y >= axeY) return null;
        return cases[x][y];
    }

}

