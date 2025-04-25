// === Décorateur de déplacement des pions===

import java.util.*;

class DeplacementPion extends DecorateurPiece {

    public DeplacementPion(Piece piece) {
        super(piece);
    }

    @Override
    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        List<Position> positions = new ArrayList<>();
        int direction = estBlanche() ? -1 : 1;
        int x = pos.x + direction;

        if (x >= 0 && x < plateau.getAxeX() && !plateau.estOccupe(new Position(x, pos.y))) {
            positions.add(new Position(x, pos.y));
        }


        if ((estBlanche() && pos.x == (plateau.getAxeX()-2)) || (!estBlanche() && pos.x == 1)) {
            Position deuxCases = new Position(pos.x + 2 * direction, pos.y);

            if (!plateau.estOccupe(new Position(x, pos.y)) && !plateau.estOccupe(deuxCases)) {
                positions.add(deuxCases);

            }
        }

        for (int dy : new int[]{-1, 1}) {
            int y = pos.y + dy;
            if (y >= 0 && y < plateau.getAxeY() && x >= 0 && x < plateau.getAxeX()) {
                Position diag = new Position(x, y);
                if (plateau.estOccupe(diag) && plateau.getPiece(diag.x, diag.y).estBlanche() != estBlanche()) {
                    positions.add(diag);
                }
            }
        }

        return positions;
    }
}