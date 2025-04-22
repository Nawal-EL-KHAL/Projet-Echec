public class Joueur {
    private String nom;
    private boolean estBlanc;

    public Joueur(String nom, boolean estBlanc) {
        this.nom = nom;
        this.estBlanc = estBlanc;
    }

    public String getNom() {
        return nom;
    }

    public boolean estBlanc() {
        return estBlanc;
    }
}
