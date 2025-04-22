
import java.util.*;

public class PieceNeutre implements Piece {
    private final Type typePiece;
    private boolean estBlanc;
    private String cheminImage;

    public PieceNeutre(boolean estBlanc, Type typePiece, String cheminImage) {
        this.estBlanc = estBlanc;
        this.cheminImage = cheminImage;
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
}
