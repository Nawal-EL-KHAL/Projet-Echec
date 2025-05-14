import java.util.List;


public interface Piece {
    List<Position> getDeplacementsPossibles(PlateauEchecs plateau, Position pos);
    boolean estBlanche();
    Type getTypePiece();


    public default List<Position> getDeplacementsPossibles(Plateau plateau, Position from){

        return List.of();
    }
    }