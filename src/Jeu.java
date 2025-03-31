public class Jeu extends Thread {
    protected Coup coup;
    private Boolean partieTerminée;


    public void jouerPartie(){
        while (!partieTerminée){
            Coup c = getSuivant().getCoup();
            appliquerCoup(c);
        }
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
