public abstract class Jeu extends Thread {
    protected Coup coup;
    private Boolean partieTerminée;
    private Joueur joueur;


    public void jouerPartie(){
        while (!partieTerminée){
            Coup c = getSuivant().getCoup();
            appliquerCoup(c);
        }
    }

    public void appliquerCoup(Coup coup){

    };

    private Joueur getSuivant() {
        return joueur;
    }

    @Override
    public void run() {
        jouerPartie();
    }

    public void envoyerCoup (Coup c){
        coup = c;
        synchronized(this){
            notify();
        }
    }
}
