import java.util.*;

class DecorateurDame extends DecorateurPiece {

    public DecorateurDame(Piece piece) {
        super(piece);
    }

    @Override
    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        List<Position> positions = new ArrayList<>();
        positions.addAll(super.getDeplacementsPossibles(plateau, pos));  // Copie les déplacements simples

        int[] dx = {-1, -1, 1, 1};
        int[] dy = {-1, 1, -1, 1};

        for (int dir = 0; dir < 4; dir++) {
            int x = pos.x + dx[dir];
            int y = pos.y + dy[dir];
            boolean aCapture = false;

            while (x >= 0 && x < plateau.getAxeX() && y >= 0 && y < plateau.getAxeY()) {
                Position p = new Position(x, y);
                if (!plateau.estOccupe(p)) {
                    if (!aCapture) {
                        positions.add(p); // déplacement simple tant qu'on n'a pas sauté
                    } else {
                        positions.add(p); // case après la prise
                        break; // après une prise, on s'arrête
                    }
                } else {
                    // Il y a une pièce sur la case
                    if (!plateau.estOccupeParAllie(p, estBlanche())) {
                        // Si c'est une pièce ennemie, on regarde la case suivante
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];
                        if (nx >= 0 && nx < plateau.getAxeX() && ny >= 0 && ny < plateau.getAxeY()) {
                            Position apres = new Position(nx, ny);
                            if (!plateau.estOccupe(apres)) {
                                positions.add(apres); // saut possible
                                aCapture = true;
                                x = nx;
                                y = ny;
                                continue; // on continue à explorer (pour double prise)
                            }
                        }
                    }
                    break; // pièce alliée ou pas de saut possible → on arrête
                }

                x += dx[dir];
                y += dy[dir];
            }
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
