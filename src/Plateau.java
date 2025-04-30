
import java.util.List;
import java.util.Observable;

public class Plateau extends Observable {
    private Piece[][] cases;
    private int axeX;
    private int axeY;

    public Plateau() {
        axeX = 8;
        axeY = 8;
        cases = new Piece[axeX][axeY];
    }

    public void initialiserGrille() {
        // Pièces noires
        placerPiece(new DeplacementDiagonale(new DeplacementLigne(new PieceNeutre(false, Type.Reine))), 0, 3);
        placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 4);
        placerPiece(new DeplacementLigne(new PieceNeutre(false, Type.Tour)), 0, 0);
        placerPiece(new DeplacementLigne(new PieceNeutre(false, Type.Tour)), 0, 7);
        placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier)), 0, 1);
        placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier)), 0, 6);
        placerPiece(new DeplacementDiagonale(new PieceNeutre(false, Type.Fou)), 0, 2);
        placerPiece(new DeplacementDiagonale(new PieceNeutre(false, Type.Fou)), 0, 5);
        for (int i = 0; i < 8; i++) {
            placerPiece(new DeplacementPion(new PieceNeutre(false, Type.Pion)), 1, i);
        }

        // Pièces blanches
        placerPiece(new DeplacementLigne(new DeplacementDiagonale(new PieceNeutre(true, Type.Reine))), 7, 3);
        placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 4);
        placerPiece(new DeplacementLigne(new PieceNeutre(true, Type.Tour)), 7, 0);
        placerPiece(new DeplacementLigne(new PieceNeutre(true, Type.Tour)), 7, 7);
        placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier)), 7, 1);
        placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier)), 7, 6);
        placerPiece(new DeplacementDiagonale(new PieceNeutre(true, Type.Fou)), 7, 2);
        placerPiece(new DeplacementDiagonale(new PieceNeutre(true, Type.Fou)), 7, 5);
        for (int i = 0; i < 8; i++) {
            placerPiece(new DeplacementPion(new PieceNeutre(true, Type.Pion)), 6, i);
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

    public void demoPromotion() {
        // Pion blanc prêt à être promu
        placerPiece(new DeplacementPion(new PieceNeutre(true, Type.Pion)), 1, 4);

        // Roi blanc
        placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        // Roi noir
        placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 0);

        relationVue();
    }

    public void demoEchec() {
        // Roi noir
        placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 4);

        // Reine blanche qui attaque mais le roi peut encore fuir
        placerPiece(new DeplacementDiagonale(new DeplacementLigne(new PieceNeutre(true, Type.Reine))), 1, 4);

        // Roi blanc
        placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        relationVue();
    }

    public void demoEchecEtMat() {
        // Roi noir coincé dans un coin
        placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 0);

        // Reine blanche qui donne mat
        placerPiece(new DeplacementLigne(new DeplacementDiagonale(new PieceNeutre(true, Type.Reine))), 1, 1);

        // Roi blanc à distance (ne participe pas à l’échec et mat mais nécessaire pour validité)
        placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        relationVue();
    }

    public void demoPriseEnPassant() {
        Piece pionBlanc = new DeplacementPion(new PieceNeutre(true, Type.Pion));
        Piece pionNoir = new DeplacementPion(new PieceNeutre(false, Type.Pion));
        Piece roiBlanc = new DecorateurRoi(new PieceNeutre(true, Type.Roi));
        Piece roiNoir = new DecorateurRoi(new PieceNeutre(false, Type.Roi));

        // Placer les rois pour éviter les erreurs d’échec
        placerPiece(roiBlanc, 7, 4);
        placerPiece(roiNoir, 0, 4);

        // Le pion blanc vient de faire un double pas de (6,4) à (4,4)
        placerPiece(pionBlanc, 6, 4);

        // Le pion noir est en position de capture en passant à gauche du pion blanc
        placerPiece(pionNoir, 4, 3);

        relationVue();
    }



}

