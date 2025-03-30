import java.util.Observable;

public class Model extends Observable {
    public int i;
    public int j;

    public void set(int i, int j){
        this.i = i;
        this.j = j;
        setChanged(); // Indique qu’un changement a eu lieu
        notifyObservers();  // Notifie tous les observateurs via update()
    }
}
