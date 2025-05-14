import java.util.List;

public abstract class Jeu {
    protected Joueur joueurBlanc;
    protected Joueur joueurNoir;
    protected Joueur joueurActuel;
    protected Plateau plateau;
    protected Position caseSelectionnee = null;
    protected boolean estTermine = false;


    public Jeu(Plateau plateau) {
        this.plateau = plateau;
        this.joueurBlanc = new Joueur("Blanc", true);
        this.joueurNoir = new Joueur("Noir", false);
        this.joueurActuel = joueurBlanc;
    }


    public abstract void gererClic(int x, int y);


    public Joueur getJoueurActuel() {
        return joueurActuel;
    }


    public abstract boolean estTourDuJoueur(Position position);

    private void changerTour() {
        joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
    }


    public boolean estTermine() {
        return estTermine;
    }


}