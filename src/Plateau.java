import java.util.Observable;

public class Plateau extends Observable {
    private Piece[][] cases;

    public Plateau() {
        cases = new Piece[8][8];
    }

    public void placerPiece(Piece piece, int x, int y) {
        cases[x][y] = piece;
        setChanged();
        notifyObservers();
    }

    public Piece getPiece(int x, int y) {
        return cases[x][y];
    }

    public boolean estOccupe(Position p) {
        return getPiece(p.x, p.y) != null;
    }

    public boolean estOccupeParAllie(Position p, boolean estBlanc) {
        Piece cible = getPiece(p.x, p.y);
        return cible != null && cible.estBlanche() == estBlanc;
    }
}
