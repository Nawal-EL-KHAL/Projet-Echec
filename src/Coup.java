public class Coup {
    final int departX, departY;
    final int arriveeX, arriveeY;

    public Coup(int departX, int departY, int arriveeX, int arriveeY) {
        this.departX = departX;
        this.departY = departY;
        this.arriveeX = arriveeX;
        this.arriveeY = arriveeY;
    }

    public int getDepartX() {
        return departX;
    }

    public int getDepartY() {
        return departY;
    }

    public int getArriveeX() {
        return arriveeX;
    }

    public int getArriveeY() {
        return arriveeY;
    }
}
