public class PlateauEchecs extends Plateau {

    public PlateauEchecs() {
        axeX = 8;
        axeY = 8;
        cases = new Piece[axeX][axeY];
        damier = true;
    }

    public void initialiserGrille() {
        // Pièces noires
        placerPiece(new DecorateurDiagonale(new DecorateurLigne(new PieceNeutre(false, Type.Reine))), 0, 3);
        placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 4);
        placerPiece(new DecorateurLigne(new PieceNeutre(false, Type.Tour)), 0, 0);
        placerPiece(new DecorateurLigne(new PieceNeutre(false, Type.Tour)), 0, 7);
        placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier)), 0, 1);
        placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier)), 0, 6);
        placerPiece(new DecorateurDiagonale(new PieceNeutre(false, Type.Fou)), 0, 2);
        placerPiece(new DecorateurDiagonale(new PieceNeutre(false, Type.Fou)), 0, 5);
        for (int i = 0; i < 8; i++) {
            placerPiece(new DecorateurPion(new PieceNeutre(false, Type.Pion)), 1, i);
        }

        // Pièces blanches
        placerPiece(new DecorateurLigne(new DecorateurDiagonale(new PieceNeutre(true, Type.Reine))), 7, 3);
        placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 4);
        placerPiece(new DecorateurLigne(new PieceNeutre(true, Type.Tour)), 7, 0);
        placerPiece(new DecorateurLigne(new PieceNeutre(true, Type.Tour)), 7, 7);
        placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier)), 7, 1);
        placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier)), 7, 6);
        placerPiece(new DecorateurDiagonale(new PieceNeutre(true, Type.Fou)), 7, 2);
        placerPiece(new DecorateurDiagonale(new PieceNeutre(true, Type.Fou)), 7, 5);
        for (int i = 0; i < 8; i++) {
            placerPiece(new DecorateurPion(new PieceNeutre(true, Type.Pion)), 6, i);
        }

        relationVue();
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

    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return null;
        return cases[x][y];
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

