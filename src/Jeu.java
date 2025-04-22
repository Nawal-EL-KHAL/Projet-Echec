import java.util.*;

public class Jeu {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Joueur joueurActuel;
    private Plateau plateau;

    private boolean blancEnCours;
    private boolean estTermine = false;


    public Jeu(Plateau plateau) {
        this.plateau = plateau;
        this.joueurBlanc = new Joueur("Blanc", true);
        this.joueurNoir = new Joueur("Noir", false);
        this.joueurActuel = joueurBlanc;
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
                plateau.placerPiece(piece, to.x, to.y);
                plateau.placerPiece(null, from.x, from.y);
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
        blancEnCours = true;
        System.out.println("La partie commence. Les blancs jouent en premier.");
    }

    public boolean estTermine() {
        return estTermine;
    }

    public boolean estBlancEnCours() {
        return blancEnCours;
    }
}
