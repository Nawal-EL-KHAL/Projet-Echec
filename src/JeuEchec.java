import java.util.*;
import java.util.List;

public class JeuEchec extends Jeu {
    private boolean promotionEnAttente = false;
    private Position positionPromotion;
    private boolean couleurPromotion;
    private Position positionPionVulnerable = null;


    public JeuEchec(Plateau plateau) {
        super(plateau);
    }

    public void gererClic(int x, int y) {
        if (estTermine()) {
            System.out.println("La partie est terminée !");
            return;
        }

        Position posCliquee = new Position(x, y);

        if (super.getCaseSelectionnee() == null) {
            if (estTourDuJoueur(posCliquee)) {
                super.setCaseSelectionnee(posCliquee);
                plateau.couleur();
                plateau.afficherCasesAccessibles();
            } else {
                System.out.println("Ce n'est pas ton tour !");
            }
        } else {
            if (!posCliquee.equals(super.getCaseSelectionnee())) {
                boolean deplacementReussi = tenterDeplacer(super.getCaseSelectionnee(), posCliquee);
                if (!deplacementReussi) {
                    System.out.println("Déplacement invalide !");
                } else {
                    if (estPositionEchec()) {
                        System.out.println("Échec au roi " + (super.getJoueurActuel().estBlanc() ? "blanc" : "noir"));
                    }

                    if (estTermine()) {
                        System.out.println("Fin de partie. Le joueur " + (super.getJoueurActuel().estBlanc() ? "noir" : "blanc") + " a gagné !");
                    }
                }
            }
            plateau.viderSelection();
            plateau.viderCasesAccessibles();
            super.setCaseSelectionnee(null);

        }
    }



    public boolean estTourDuJoueur(Position position) {
        Piece piece = plateau.getPiece(position.x, position.y);
        return piece != null && piece.estBlanche() == super.getJoueurActuel().estBlanc();
    }

    public boolean tenterDeplacer(Position from, Position to) {
        Piece piece = plateau.getPiece(from.x, from.y);
        if (piece != null && piece.estBlanche() == super.getJoueurActuel().estBlanc()) {
            List<Position> deplacements = piece.getDeplacementsPossibles(plateau, from);
            if (deplacements.contains(to)) {
                // On sauvegarde l'état
                Piece cible = plateau.getPiece(to.x, to.y);
                plateau.viderCasesAccessibles();

                // --- Gestion prise en passant ---
                if (piece.getTypePiece() == Type.Pion && cible == null && from.y != to.y) {
                    // Le pion capture en diagonale sans qu'une pièce soit présente : c'est une prise en passant
                    int direction = piece.estBlanche() ? 1 : -1;
                    plateau.placerPiece(null, to.x + direction, to.y); // Supprime le pion pris en passant
                }

                plateau.placerPiece(piece, to.x, to.y);
                plateau.placerPiece(null, from.x, from.y);

                // Si le roi du joueur actuel est en échec après le déplacement, on annule
                if (estPositionEchecPour(super.getJoueurActuel().estBlanc())) {
                    // Annulation du déplacement
                    plateau.viderCasesAccessibles();
                    plateau.placerPiece(piece, from.x, from.y);
                    plateau.placerPiece(cible, to.x, to.y);

                    // Si c'était une prise en passant annulée, il faut aussi restaurer la pièce capturée
                    if (piece.getTypePiece() == Type.Pion && cible == null && from.y != to.y) {
                        int direction = piece.estBlanche() ? 1 : -1;
                        // Replacer le pion capturé
                        Piece pionCapture = new DeplacementPion(new PieceNeutre(! super.getJoueurActuel().estBlanc(), Type.Pion));
                        plateau.placerPiece(pionCapture, to.x + direction, to.y);
                    }

                    return false;
                }

                gererPromotion(to, piece);

                // --- Mémoriser si le pion vient d'avancer de 2 cases ---
                if (piece.getTypePiece() == Type.Pion && Math.abs(from.x - to.x) == 2) {
                    positionPionVulnerable = to;
                } else {
                    positionPionVulnerable = null; // Sinon, plus de vulnérabilité
                }

                super.changerTour();
                return true;
            }
        }
        return false;
    }



    public void commencer() {
        super.setEstTermine(false);
        if (estPositionEchec()) {
            System.out.println("Échec au roi " + (super.getJoueurActuel().estBlanc() ? "blanc" : "noir") + " !");
        }
    }

    public boolean estTermine() {
        return super.getEstTermine();
    }


    public boolean estPositionEchec() {
        return estPositionEchecPour(super.getJoueurActuel().estBlanc());
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
        return super.getCaseSelectionnee();
    }

    public boolean estPromotionEnAttente() {
        return promotionEnAttente;
    }

    public void traiterPromotion(String choix) {
        if (promotionEnAttente && positionPromotion != null) {
            Piece nouvellePiece = null;
            boolean estBlanc = couleurPromotion;

            switch (choix) {
                case "Reine":
                    nouvellePiece = new DeplacementDiagonale(new DeplacementLigne(new PieceNeutre(estBlanc, Type.Reine)));
                    break;
                case "Tour":
                    nouvellePiece = new DeplacementLigne(new PieceNeutre(estBlanc, Type.Tour));
                    break;
                case "Fou":
                    nouvellePiece = new DeplacementDiagonale(new PieceNeutre(estBlanc, Type.Fou));
                    break;
                case "Cavalier":
                    nouvellePiece = new DecorateurCavalier(new PieceNeutre(estBlanc, Type.Cavalier));
                    break;
                default:
                    nouvellePiece = new DeplacementDiagonale(new DeplacementLigne(new PieceNeutre(estBlanc, Type.Reine)));
            }

            plateau.placerPiece(nouvellePiece, positionPromotion.x, positionPromotion.y);
            promotionEnAttente = false;
            positionPromotion = null;
            couleurPromotion = false;
        }
    }

    private void gererPromotion(Position pos, Piece piece) {
        if (piece.getTypePiece() == Type.Pion) {
            if ((piece.estBlanche() && pos.x == 0) || (!piece.estBlanche() && pos.x == 7)) {
                promotionEnAttente = true;
                positionPromotion = pos;
                couleurPromotion = piece.estBlanche();
                plateau.promotion(); // prévenir la vue
            }
        }
    }
}
