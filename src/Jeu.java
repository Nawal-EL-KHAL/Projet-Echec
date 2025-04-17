public abstract class Jeu extends Thread {
    protected Coup coup;
    private boolean partieTerminee = false;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    protected Boolean partieTerminée;
    protected Joueur joueur;

    public Jeu(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.joueurCourant = joueur1;
    }

    public void jouerPartie() {
        while (!partieTerminee) {
            Coup c = joueurCourant.getCoup(); // On récupère le coup du joueur courant
            appliquerCoup(c);                 // On applique le coup
            changerJoueur();                  // Puis on passe au joueur suivant
        }
    }

    public void appliquerCoup(Coup coup) {
        // À implémenter dans la sous-classe concrète
        System.out.println("Coup appliqué : " + coup);
    }

    private void changerJoueur() {
        joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
    }

    @Override
    public void run() {
        jouerPartie();
    }

    public void envoyerCoup(Coup c) {
        this.coup = c;
        synchronized(this) {
            notify(); // Utilisé si tu fais attendre le thread ailleurs
        }
    }
}
