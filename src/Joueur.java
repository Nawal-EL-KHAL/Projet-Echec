public class Joueur {

    private Jeu jeu;

    public Coup getCoup(){
        synchronized (jeu) {
            wait();
        }
    };
    return jeu.coup;
}
