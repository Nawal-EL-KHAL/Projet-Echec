import java.util.*;

public abstract class DecorateurPiece implements Piece {
    protected Piece piece;

    public DecorateurPiece(Piece piece) {
        this.piece = piece;
    }

    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        return piece.getDeplacementsPossibles(plateau, pos);
    }

    public boolean estBlanche() {
        return piece.estBlanche();
    }



    public Type getTypePiece() {
        return piece.getTypePiece();
    }
}