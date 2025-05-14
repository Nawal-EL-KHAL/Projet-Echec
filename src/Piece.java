import java.util.List;


public interface Piece {
    List<Position> getDeplacementsPossibles(PlateauEchecs plateau, Position pos);
    boolean estBlanche();
    Type getTypePiece();
    boolean aBouge();
    void setABouge(boolean val);
    }