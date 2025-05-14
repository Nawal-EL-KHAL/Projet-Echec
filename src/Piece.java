import java.util.List;


public interface Piece {
    List<Position> getDeplacementsPossibles(PlateauEchecs plateau, Position pos);
    boolean estBlanche();
    Type getTypePiece();
    boolean getABouge();
    void setABouge(boolean aBouge);


}