import java.util.*;
import java.util.List;

public class Jeu {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Joueur joueurActuel;
    private Plateau plateau;
    private Position caseSelectionnee = null;

    private boolean estTermine = false;

    public Jeu(Plateau plateau) {
        this.plateau = plateau;
        this.joueurBlanc = new Joueur("Blanc", true);
        this.joueurNoir = new Joueur("Noir", false);
        this.joueurActuel = joueurBlanc;
    }

    public void gererClic(int x, int y) {
        if (estTermine()) {
            System.out.println("La partie est terminée !");
            return;
        }

        Position posCliquee = new Position(x, y);
        Piece pieceCliquee = plateau.getPiece(x, y);

        if (caseSelectionnee == null) {
            if (estTourDuJoueur(posCliquee)) {
                caseSelectionnee = posCliquee;
                plateau.couleur();
                plateau.afficherCasesAccessibles();
            } else {
                System.out.println("Ce n'est pas ton tour !");
            }
        } else {
            if (!posCliquee.equals(caseSelectionnee)) {
                boolean deplacementReussi = tenterDeplacer(caseSelectionnee, posCliquee);
                if (!deplacementReussi) {
                    System.out.println("Déplacement invalide !");
                } else {
                    if (estPositionEchec()) {
                        System.out.println("Échec au roi " + (joueurActuel.estBlanc() ? "blanc" : "noir"));
                    }

                    if (estTermine()) {
                        System.out.println("Fin de partie. Le joueur " + (joueurActuel.estBlanc() ? "noir" : "blanc") + " a gagné !");
                    }
                }
            }
            plateau.viderSelection();
            plateau.viderCasesAccessibles();
            caseSelectionnee = null;
        }
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public boolean estTourDuJoueur(Position position) {
        Piece piece = plateau.getPiece(position.x, position.y);
        return piece != null && piece.estBlanche() == joueurActuel.estBlanc();
    }

    public boolean tenterDeplacer(Position from, Position to) {
        Piece piece = plateau.getPiece(from.x, from.y);
        if (piece != null && piece.estBlanche() == joueurActuel.estBlanc()) {
            List<Position> deplacements = piece.getDeplacementsPossibles(plateau, from);
            if (deplacements.contains(to)) {
                // On sauvegarde l'état
                Piece cible = plateau.getPiece(to.x, to.y);
                plateau.viderCasesAccessibles();
                plateau.placerPiece(piece, to.x, to.y);
                plateau.placerPiece(null, from.x, from.y);

                // Si le roi du joueur actuel est en échec après le déplacement, on annule
                if (estPositionEchecPour(joueurActuel.estBlanc())) {
                    plateau.viderCasesAccessibles();
                    plateau.placerPiece(piece, from.x, from.y);
                    plateau.placerPiece(cible, to.x, to.y);
                    return false;
                }

                changerTour();
                return true;
            }
        }
        return false;
    }

    private void changerTour() {
        joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
    }

    public void commencer() {
        estTermine = false;
        if (estPositionEchec()) {
            System.out.println("Échec au roi " + (joueurActuel.estBlanc() ? "blanc" : "noir") + " !");
        }
    }

    public boolean estTermine() {
        return estTermine;
    }


    public boolean estPositionEchec() {
        return estPositionEchecPour(joueurActuel.estBlanc());
    }

    private boolean estPositionEchecPour(boolean blanc) {
        Position positionRoi = null;

        // 1. Trouver la position du roi du joueur concerné
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = plateau.getPiece(i, j);
                if (p != null && p.estBlanche() == blanc && p.getTypePiece() == Type.Roi) {
                    positionRoi = new Position(i, j);
                    break;
                }
            }
        }

        if (positionRoi == null) return false; // Pas de roi trouvé (bug ?)

        // 2. Parcourir les pièces ennemies
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = plateau.getPiece(i, j);
                if (p != null && p.estBlanche() != blanc) {
                    List<Position> deplacements = p.getDeplacementsPossibles(plateau, new Position(i, j));
                    if (deplacements.contains(positionRoi)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Position getCaseSelectionnee() {
        return caseSelectionnee;
    }
}
