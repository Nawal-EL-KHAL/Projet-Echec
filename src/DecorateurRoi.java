// === Décorateur de déplacement des pions===

import java.util.*;

class DecorateurRoi extends DecorateurPiece {

    public DecorateurRoi(Piece piece) {
        super(piece);
    }

    @Override
    public List<Position> getDeplacementsPossibles(PlateauEchecs plateau, Position pos) {
        List<Position> positions = new ArrayList<>();
        positions.addAll(super.getDeplacementsPossibles(plateau, pos));  // Copie les déplacements de la pièce de base

        int[] dx = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int dir = 0; dir < 8; dir++) {
            int x = pos.x + dx[dir];
            int y = pos.y + dy[dir];

            if ((x >= 0 && x < plateau.getAxeX() && y >= 0 && y < plateau.getAxeY()) && (plateau.estOccupeParAllie(new Position(x, y), estBlanche()))) continue;
            positions.add(new Position(x, y));

        }
        return positions;
    }

    @Override
    public boolean getABouge() {
        return piece.getABouge();
    }

    @Override
    public void setABouge(boolean aBouge) {
        piece.setABouge(aBouge);
    }
}