// === Décorateur de déplacement en ligne ===
import java.util.*;

class DeplacementLigne extends DecorateurPiece {

    public DeplacementLigne(Piece piece) {
        super(piece);
    }

    @Override
    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        List<Position> positions = new ArrayList<>();
        positions.addAll(super.getDeplacementsPossibles(plateau, pos));  // Copie les déplacements de la pièce de base

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        for (int dir = 0; dir < 4; dir++) {
            int x = pos.x + dx[dir];
            int y = pos.y + dy[dir];
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (plateau.estOccupeParAllie(new Position(x, y), estBlanche())) break;
                positions.add(new Position(x, y));
                if (plateau.estOccupe(new Position(x, y))) break;
                x += dx[dir];
                y += dy[dir];
            }
        }

        return positions;
    }
}
