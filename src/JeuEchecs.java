import java.util.List;

public class JeuEchecs extends Jeu {

    protected boolean promotionEnAttente = false;
    protected Position positionPromotion;
    protected boolean couleurPromotion; // blanc ou noir

    public JeuEchecs(PlateauEchecs plateau) {
        super(plateau);
    }

    @Override
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
                    System.out.println("Déplacement illégal !");
                } else {
                    // Le tour a changé dans tenterDeplacer()
                    boolean blancActuel = joueurActuel.estBlanc();
                    if (estPositionEchecPour(blancActuel)) {
                        if (estEchecEtMat(blancActuel)) {
                            System.out.println("Échec et mat ! Le joueur " + (blancActuel ? "noir" : "blanc") + " a gagné !");
                            estTermine = true;
                        } else {
                            System.out.println("Échec au roi " + (blancActuel ? "blanc." : "noir."));
                        }
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
        List<Position> deplacements = piece.getDeplacementsPossibles((PlateauEchecs) plateau, from);
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
                piece.setABouge(true);
                gererPromotion(to, piece);

                changerTour();
                return true;

        }
        return false;
    }

    private void changerTour() {
        joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
    }

    public void commencer() {
        estTermine = false;
        if (estPositionEchec()) {
            if (estPositionEchecEtMat()) {
                System.out.println("Échec et mat ! Le joueur " + (joueurActuel.estBlanc() ? "noir" : "blanc") + " a gagné !");
                estTermine = true;
            } else {
                System.out.println("Échec au roi " + (joueurActuel.estBlanc() ? "blanc" : "noir"));
            }
        }
    }

    public boolean estTermine() {
        return estTermine;
    }


    public boolean estPositionEchec() {
        return estPositionEchecPour(joueurActuel.estBlanc());
    }

    public boolean estPositionEchecEtMat() {
        return estEchecEtMat(joueurActuel.estBlanc());
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

                    List<Position> deplacements = p.getDeplacementsPossibles((PlateauEchecs) plateau, new Position(i, j));

                    if (deplacements.contains(positionRoi)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean estEchecEtMat(boolean blanc) {

        if (!estPositionEchecPour(blanc)) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = plateau.getPiece(i, j);
                if (piece != null && piece.estBlanche() == blanc) {
                    Position from = new Position(i, j);
                    List<Position> deplacements = piece.getDeplacementsPossibles((PlateauEchecs) plateau, from);
                    for (Position to : deplacements) {

                        if (to.x >= 0 && to.x < 8 && to.y >= 0 && to.y < 8) {
                            // Sauvegarde de l'état
                            Piece origine = piece;
                            Piece cible = plateau.getPiece(to.x, to.y);

                            // Simuler le déplacement
                            plateau.placerPiece(origine, to.x, to.y);
                            plateau.placerPiece(null, from.x, from.y);

                            boolean enEchecApres = estPositionEchecPour(blanc);

                            // Annuler la simulation
                            plateau.placerPiece(origine, from.x, from.y);
                            plateau.placerPiece(cible, to.x, to.y);

                            if (!enEchecApres) {
                                return false; // au moins un coup sauve le roi
                            }
                        }
                    }
                }
            }
        }

        return true; // Aucun coup ne permet d'éviter l'échec
    }



    public Position getCaseSelectionnee() {
        return caseSelectionnee;
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
                    nouvellePiece = new DecorateurDiagonale(new DecorateurLigne(new PieceNeutre(estBlanc, Type.Reine)));
                    break;
                case "Tour":
                    nouvellePiece = new DecorateurLigne(new PieceNeutre(estBlanc, Type.Tour));
                    break;
                case "Fou":
                    nouvellePiece = new DecorateurDiagonale(new PieceNeutre(estBlanc, Type.Fou));
                    break;
                case "Cavalier":
                    nouvellePiece = new DecorateurCavalier(new PieceNeutre(estBlanc, Type.Cavalier));
                    break;
                default:
                    nouvellePiece = new DecorateurDiagonale(new DecorateurLigne(new PieceNeutre(estBlanc, Type.Reine)));
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