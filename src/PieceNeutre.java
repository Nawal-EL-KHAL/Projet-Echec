
import java.util.*;

public class PieceNeutre implements Piece {
    private final Type typePiece;
    private boolean estBlanc;
    private boolean aBouge = false;
    private Position position;

    public PieceNeutre(boolean estBlanc, Type typePiece) {
        this.estBlanc = estBlanc;
        this.typePiece = typePiece;
    }


    @Override
    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        return Collections.emptyList(); // le déplacement vient du décorateur
    }

    @Override
    public boolean estBlanche() {
        return estBlanc;
    }

    @Override
    public Type getTypePiece() {
        return typePiece;
    }

    @Override
    public boolean getABouge() {
        return aBouge;
    }

    @Override
    public void setABouge(boolean aBouge) {
        this.aBouge = aBouge;
    }
}
