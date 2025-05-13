public class DemoPlateau extends Plateau {

    final Plateau plateau;

    public DemoPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void demoPromotion() {
        // Pion blanc prêt à être promu
        plateau.placerPiece(new DeplacementPion(new PieceNeutre(true, Type.Pion)), 1, 4);

        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        // Roi noir
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 0);

        relationVue();
    }

    public void demoEchec() {
        // Roi noir
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 4);

        // Pion noir
        plateau.placerPiece(new DeplacementPion(new PieceNeutre(false, Type.Pion)), 1, 4);

        // Reine blanche qui attaque mais le roi peut encore fuir
        plateau.placerPiece(new DeplacementDiagonale(new DeplacementLigne(new PieceNeutre(true, Type.Reine))), 2, 5);

        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        relationVue();
    }

    public void demoEchecEtMat() {
        // Fou blanc
        plateau.placerPiece(new DeplacementDiagonale(new PieceNeutre(true, Type.Fou)), 4, 4);

        // Tour blanche
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(true, Type.Tour)), 7, 1);

        // Tour blanche
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(true, Type.Tour)), 2, 7);

        // Roi noir
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 0);

        relationVue();
    }

    public void demoPriseEnPassant() {
        Piece pionBlanc = new DeplacementPion(new PieceNeutre(true, Type.Pion));
        Piece pionNoir = new DeplacementPion(new PieceNeutre(false, Type.Pion));
        Piece roiBlanc = new DecorateurRoi(new PieceNeutre(true, Type.Roi));
        Piece roiNoir = new DecorateurRoi(new PieceNeutre(false, Type.Roi));

        // Placer les rois pour éviter les erreurs d’échec
        plateau.placerPiece(roiBlanc, 7, 4);
        plateau.placerPiece(roiNoir, 0, 4);

        // Le pion blanc vient de faire un double pas de (6,4) à (4,4)
        plateau.placerPiece(pionBlanc, 6, 4);

        // Le pion noir est en position de capture en passant à gauche du pion blanc
        plateau.placerPiece(pionNoir, 4, 3);

        relationVue();
    }

    public void demoCoupIllegal() {
        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 4, 3);

        // Cavalier noir
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier)), 4, 4);

        // Tour noire
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(false, Type.Tour)), 0, 4);

        relationVue();
    }

    public void demoCoupIllegal2() {
        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 4, 3);

        // Cavalier blanc
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier)), 3, 3);

        // Tour noire
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(false, Type.Tour)), 0, 3);

        relationVue();
    }

}
