import java.awt.*;

public class PlateauDames extends Plateau {

    public PlateauDames() {
        axeX = 10;
        axeY = 10;
        cases = new Piece[axeX][axeY];
        damier = true;
    }

    public void initialiserGrille() {
        System.out.println("Grille de la plateau de dames");
        for (int i = 0; i < axeY; i++) {
            for (int j = 0; j < 4; j++) {

                if ((i + j) % 2 == 1){
                    placerPiece(new DecorateurDame(new PieceNeutre(false, Type.Pion)), j, i);

                }

            }

        }

        for (int i = 0; i < axeY; i++) {
            for (int j = 6; j < axeX; j++) {

                if ((i + j) % 2 == 1){
                    placerPiece(new DecorateurDame(new PieceNeutre(true, Type.Pion)), j, i);

                }
            }

        }
        relationVue();
    }

    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= axeX || y < 0 || y >= axeY) return null;
        return cases[x][y];
    }


    public void placerPiece(Piece piece, int x, int y) {
        cases[x][y] = piece;
        relationVue();
    }

    public void relationVue(){
        setChanged();
        notifyObservers("rafraichissement");
    }

    public void promotion(){
        setChanged();
        notifyObservers("promotion");
    }

    public void viderSelection() {
        setChanged();
        notifyObservers("vider");
    }

    public void couleur(){
        setChanged();
        notifyObservers("couleur");
    }

    public void afficherCasesAccessibles() {
        setChanged();
        notifyObservers("accessibilite");
    }

    public void viderCasesAccessibles() {
        setChanged();
        notifyObservers("viderCA");
    }



    public boolean estOccupe(Position p) {
        return getPiece(p.x, p.y) != null;
    }

    public boolean estOccupeParAllie(Position p, boolean estBlanc) {
        Piece cible = getPiece(p.x, p.y);
        return cible != null && cible.estBlanche() == estBlanc;
    }

    public int getAxeX() {
        return axeX;
    }

    public int getAxeY() {
        return axeY;
    }

    public boolean isDamier() {
        return damier;
    }
}

