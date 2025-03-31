public class Joueur {

    protected Jeu jeu;

    public Coup getCoup(){
        synchronized (jeu) {
            try {
                 jeu.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jeu.coup;
    }
}
