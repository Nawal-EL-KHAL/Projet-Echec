import java.util.List;

public class JeuDames extends Jeu {

    protected PlateauDames plateau;


    public JeuDames(PlateauDames plateauDames) {
        super(plateauDames);
        this.plateau = plateauDames;
    }

    @Override
    public void gererClic(int x, int y) {
        if (estTermine()) {
            System.out.println("La partie est terminée !");
            return;
        }

        Position posCliquee = new Position(x, y);

        if (caseSelectionnee == null) {
            // Première sélection
            if (estTourDuJoueur(posCliquee)) {
                caseSelectionnee = posCliquee;
                plateau.couleur();
                plateau.afficherCasesAccessibles();
            } else {
                System.out.println("Ce n'est pas ton tour !");
            }
        } else {
            // Deuxième clic
            if (!posCliquee.equals(caseSelectionnee)) {
                Piece piece = plateau.getPiece(caseSelectionnee.x, caseSelectionnee.y);
                List<Position> deplacementsPossibles = piece.getDeplacementsPossibles(plateau, caseSelectionnee);

                if (deplacementsPossibles.contains(posCliquee)) {
                    // Gérer une prise si nécessaire
                    gererPrise(caseSelectionnee, posCliquee);

                    // Déplacer la pièce
                    plateau.placerPiece(piece, posCliquee.x, posCliquee.y);
                    plateau.placerPiece(null, caseSelectionnee.x, caseSelectionnee.y);
                    piece.setABouge(true);

                    // Changer de tour
                    changerTour();
                } else {
                    System.out.println("Déplacement illégal !");
                }
            }

            // Réinitialiser la sélection
            plateau.viderSelection();
            plateau.viderCasesAccessibles();
            caseSelectionnee = null;
        }
    }

    private void gererPrise(Position caseSelectionnee, Position posCliquee) {
    }


    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public boolean estTourDuJoueur(Position position) {
        Piece piece = plateau.getPiece(position.x, position.y);
        return piece != null && piece.estBlanche() == joueurActuel.estBlanc();
    }


    private void changerTour() {
        joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
    }

    public boolean estTermine() {
        return estTermine;
    }





    public Position getCaseSelectionnee() {
        return caseSelectionnee;
    }




}