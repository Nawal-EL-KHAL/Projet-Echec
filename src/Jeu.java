import java.util.*;
import java.util.List;

public abstract class Jeu {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Joueur joueurActuel;
    protected Plateau plateau;
    private Position caseSelectionnee = null;
    private boolean estTermine = false;


    public Jeu(Plateau plateau) {
        this.plateau = plateau;
        this.joueurBlanc = new Joueur("Blanc", true);
        this.joueurNoir = new Joueur("Noir", false);
        this.joueurActuel = joueurBlanc;
    }

    public abstract void gererClic(int x, int y);

    public void changerTour() {
        joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
    }

    public abstract void commencer();

    public boolean estTermine() {
        return estTermine;
    }

    public Position getCaseSelectionnee() {
        return caseSelectionnee;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public boolean getEstTermine() {
        return estTermine;
    }

    public void setEstTermine(boolean estTermine) {
        this.estTermine = estTermine;
    }

    public void setCaseSelectionnee(Position caseSelectionnee) {
        this.caseSelectionnee = caseSelectionnee;
    }
}
