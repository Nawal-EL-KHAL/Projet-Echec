import java.util.Observable;

public class Model extends Observable {
    public int i;
    public int j;

    public void set(int i, int j){
        this.i = i;
        this.j = j;
        setChanged();
        notifyObservers();
    }
}
