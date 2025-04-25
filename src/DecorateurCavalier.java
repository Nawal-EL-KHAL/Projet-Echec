// === Décorateur de déplacement des pions===

import java.util.*;

class DecorateurCavalier extends DecorateurPiece {

    public DecorateurCavalier(Piece piece) {
        super(piece);
    }

    @Override
    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        List<Position> positions = new ArrayList<>();
        positions.addAll(super.getDeplacementsPossibles(plateau, pos));  // Copie les déplacements de la pièce de base

        int[] dx = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dy = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int dir = 0; dir < 8; dir++) {
            int x = pos.x + dx[dir];
            int y = pos.y + dy[dir];

            if ((x >= 0 && x < plateau.getAxeX() && y >= 0 && y < plateau.getAxeY()) && (plateau.estOccupeParAllie(new Position(x, y), estBlanche()))) continue;
            positions.add(new Position(x, y));

        }

        return positions;
    }
}