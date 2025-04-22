import javax.swing.*;
import java.util.List;

public interface Piece {
    List<Position> getDeplacementsPossibles(Plateau plateau, Position pos);
    boolean estBlanche();
    Icon getIcon();
    String getNom();
}
