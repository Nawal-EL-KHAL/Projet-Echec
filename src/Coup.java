public class Coup {
<<<<<<< HEAD
    protected int xDep;
    protected int yDep;
=======
    private int departX, departY;
    private int arriveeX, arriveeY;

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
>>>>>>> 4a042e22b7c1c1ed4738c094b3715a38fc083665
}
